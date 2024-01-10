/**
 * Export a thumbnail image, with and without an overlay, using QuPath.
 *
 * For tissue microarrays, the scripting code written by the 'File -> Export TMA data'
 * command is probably more appropriate.
 *
 * However, for all other kinds of images where batch export is needed this script can be used.
 *
 * @author Pete Bankhead
 */


import qupath.lib.images.writers.ImageWriterTools
import qupath.lib.gui.QuPathGUI
import qupath.lib.gui.viewer.OverlayOptions
import qupath.lib.regions.RegionRequest
import qupath.lib.gui.scripting.QPEx

name = GeneralTools.getNameWithoutExtension(getCurrentImageData().getServer().getMetadata().getName())

// Aim for an output resolution of approx 0.5 um/pixel
double requestedPixelSize = 0.5


// Define format
def formatExtensions = [
        'PNG': '.png',
        'JPEG': '.jpg'
]
def format = 'PNG'



// Create the output directory, if required
def path = QPEx.buildFilePath(QPEx.PROJECT_BASE_DIR, "screenshots")
QPEx.mkdirs(path)


// Get the imageData & server
def imageData = QPEx.getCurrentImageData()
def server = imageData.getServer()
def viewer = QPEx.getCurrentViewer()



// Get the file name from the current server
//def name = server.getShortServerName()

name = GeneralTools.getNameWithoutExtension(getCurrentImageData().getServer().getMetadata().getName())

// We need to get the display settings (colors, line thicknesses, opacity etc.) from the current viewer, if available
def overlayOptions = QuPathGUI.getInstance() == null ? new OverlayOptions() : viewer.getOverlayOptions()



// Calculate downsample factor depending on the requested pixel size
//double downsample = requestedPixelSize / server.getAveragedPixelSizeMicrons()
downsample = 2
def request = RegionRequest.createInstance(imageData.getServerPath(), downsample, 0, 0, server.getWidth(), server.getHeight())


// Write output image, with and without overlay
def dir = new File(path)
def fileImage = new File(dir, name + formatExtensions[format])


def img = server.readBufferedImage(request)
img = viewer.getImageDisplay().applyTransforms(img, null)


javax.imageio.ImageIO.write(img, format, fileImage)
def fileImageWithOverlay = new File(dir, name + "-overlay" + formatExtensions[format])

ImageWriterTools.writeImageRegionWithOverlay(img, imageData, overlayOptions, request, fileImageWithOverlay.getAbsolutePath())