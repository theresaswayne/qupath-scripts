
// setup
downsample = 2
//def imageData = getCurrentImageData()
//def server = getCurrentServer()
name = GeneralTools.getNameWithoutExtension(getCurrentImageData().getServer().getMetadata().getName())
def path = buildFilePath(PROJECT_BASE_DIR, "snapshots", name+'_prediction.png')


// Write the full image, displaying objects according to how they are currently shown in the viewer
// does not work in batch - - uses only the current image
// fill annotations or increase line width for better visibility
//def viewer = getCurrentViewer()
//writeRenderedImage(viewer, path)


import qupath.imagej.tools.IJTools
import qupath.lib.gui.images.servers.RenderedImageServer
import qupath.lib.gui.viewer.overlays.HierarchyOverlay
import qupath.lib.regions.RegionRequest

import static qupath.lib.gui.scripting.QPEx.*

// Request the current viewer for settings, and current image (which may be used in batch processing)
def viewer = getCurrentViewer()
def imageData = getCurrentImageData()

//This code block will use individual viewer settings for each image 
//- meaning you could turn off some channels in different images and the export would pick up on that
//Comment this block out and use the block below to use current viewer settings for all images
////////////////////////////////////////////////////
//def display = new qupath.lib.display.ImageDisplay(imageData)
// Create a rendered server that includes a hierarchy overlay using the current display settings
//def server = new RenderedImageServer.Builder(imageData)
//    .display(display)
//    .downsamples(downsample)
//    .layers(new HierarchyOverlay(viewer.getImageRegionStore(), viewer.getOverlayOptions(), imageData))
//    .build()
/////////////////////////////////////////////////////

//def classifier = loadPixelClassifier('FibrosisQuant')
//def predictionServer = PixelClassifierTools.createPixelClassificationServer(imageData, classifier)
//writeImageRegion(predictionServer, RegionRequest.createInstance(predictionServer, downsample), path)


// Comment out the above code and use this code to use the SAME viewer settings as the current viewer

// this includes annotations if visible but not predictions even if the classifier window is open
// Create a rendered server that includes a hierarchy overlay using the current display settings
def display = new qupath.lib.display.ImageDisplay(imageData)
def server = new RenderedImageServer.Builder(imageData)
    .display(viewer.getImageDisplay())
    .downsamples(downsample)
    .layers(new HierarchyOverlay(viewer.getImageRegionStore(), viewer.getOverlayOptions(), imageData))
    .build()

// Write or display the rendered image
if (path != null) {
    mkdirs(new File(path).getParent())
    writeImage(server, path)
} else
    IJTools.convertToImagePlus(server, RegionRequest.createInstance(server)).getImage().show()