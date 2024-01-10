name = GeneralTools.getNameWithoutExtension(getCurrentImageData().getServer().getMetadata().getName())
def path = buildFilePath(PROJECT_BASE_DIR, "snapshots", name+'_prediction.png')
def server = getCurrentServer()

// Write the full image (only possible if it isn't too large!)
// writeImage(server, path)

// Write the full image downsampled by a factor
// no annotations or predictions
//def requestFull = RegionRequest.createInstance(server, downsample)
//writeImageRegion(server, requestFull, path)

// Write the full image, displaying objects according to how they are currently shown in the viewer
// works for prediction if you load the classifier on one image
// but doesn't show annotations even if visible on screen
//def viewer = getCurrentViewer()
//writeRenderedImage(viewer, path)

def region = RegionRequest.createInstance(server.getPath(), 2)
def img = server.readBufferedImage(region)

def snap = img.makeSnapshot()
writeImage(snap, path)