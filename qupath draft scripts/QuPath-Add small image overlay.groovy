/**
 * Add a 'small' image overlay to the image currently open in the QuPath viewer.
 * Here, 'small' indicates something that ImageIO can handle, and which isn't pyramidal.
 *
 * @author Pete Bankhead
 * 
 * Updated 1/2023 to be compatible with QuPath v0.4.x
 */


import qupath.lib.gui.viewer.ImageInterpolation
import qupath.lib.gui.viewer.overlays.BufferedImageOverlay
import qupath.lib.regions.ImageRegion

import javax.imageio.ImageIO

import static qupath.lib.gui.scripting.QPEx.*

// Define path to the image file (PNG recommended)
def path = '/path/to/image.png'

def viewer = getCurrentViewer()
def img = ImageIO.read(new File(path))

// Define the region
// Here, we assume it covers the entire image - but we can define it some other way (e.g. parsing it from the filename?)
def region = ImageRegion.createInstance(0, 0, viewer.getServerWidth(), viewer.getServerHeight(), viewer.getZPosition(), viewer.getTPosition())

// Create & add an overlay
def overlay = new BufferedImageOverlay(viewer.getOverlayOptions(), region, img)
overlay.setInterpolation(ImageInterpolation.NEAREST)
// Multiple image regions can be added if necessary
//overlay.getRegionMap().put(region2, img2)
viewer.getCustomOverlayLayers().setAll(overlay)