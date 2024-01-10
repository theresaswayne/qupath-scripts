// Write the full prediction image, downsampled 2x
//Code originally from https://forum.image.sc/t/qupath-script-with-pixel-classifier/45597/10
//This script, if Run for project, will create a subfolder in the Project directory, and place a PNG file of the each image with the current overlay settings in place.

// setup
downsample = 2

def imageData = getCurrentImageData()
//def classifier = loadPixelClassifier('Tissue regions')
def classifier = loadPixelClassifier('FibrosisQuant')
name = GeneralTools.getNameWithoutExtension(getCurrentImageData().getServer().getMetadata().getName())
mkdirs(buildFilePath(PROJECT_BASE_DIR, "snapshots"))
def predictionServer = PixelClassifierTools.createPixelClassificationServer(imageData, classifier)
def path = buildFilePath(PROJECT_BASE_DIR, "snapshots", name+'_prediction.png')

writeImageRegion(predictionServer, RegionRequest.createInstance(predictionServer, downsample), path)
