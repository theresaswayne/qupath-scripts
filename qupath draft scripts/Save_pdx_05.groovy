
// setup
downsample = 2
opacity = 0.5
def imageData = getCurrentImageData()
def server = getCurrentServer()
name = GeneralTools.getNameWithoutExtension(getCurrentImageData().getServer().getMetadata().getName())
def path = buildFilePath(PROJECT_BASE_DIR, "snapshots", name+'_prediction.png')

// Write the full image (only possible if it isn't too large!)
// writeImage(server, path)

// Write the full image downsampled by a factor
// no annotations or predictions
//def requestFull = RegionRequest.createInstance(server, downsample)
//writeImageRegion(server, requestFull, path)

// Write the full image, displaying objects according to how they are currently shown in the viewer
// works for prediction if you load the classifier on one image
// but doesn't show annotations even if visible on screen
def viewer = getCurrentViewer()
writeRenderedImage(viewer, path)

// this only writes a white image in my hands
// Create an ImageServer where the pixels are derived from annotations
//def labelServer = new LabeledImageServer.Builder(imageData)
//  .backgroundLabel(0, ColorTools.WHITE) // Specify background label (usually 0 or 255)
//  .downsample(downsample)    // Choose server resolution; this should match the resolution at which tiles are exported
//  .addLabel('Negative', 150)      // Choose output labels (the order matters!)
//  .addLabel('Positve', 200)
//  .multichannelOutput(false) // If true, each label refers to the channel of a multichannel binary image (required for multiclass probability)
//  .build()

// Write the image
//writeImage(labelServer, path)