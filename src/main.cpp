#include "IgarassuApp.h"
#include "ofAppGlutWindow.h"

//--------------------------------------------------------------
int main(){
	ofAppGlutWindow window;
	ofSetupOpenGL(&window, 600, 400, OF_WINDOW);

	ofRunApp(new IgarassuApp());
}
