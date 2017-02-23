# TUIO Processing Client and Demo Sketches

Copyright (c) 2005-2017 [Martin Kaltenbrunner](mailto:martin@tuio.org). 
This software is part of 
[reacTIVision](http://reactivision.sourceforge.net/), an open source 
fiducial tracking and multi-touch framework based on computer vision. 
For further information on the TUIO protocol and framework, please visit 
[TUIO.org](http://www.tuio.org/)

## Releases and Source Repository

You can download the release packages of this library from the 
[reacTIVision](http://reactivision.sourceforge.net/#files) software 
page, and retrieve its latest source code from the 
[GitHub](https://www.github.com/mkalten/TUIO11_Processing) 
repository.

## Demo Applications:

This package contains two demo sketches and a library which allows 
Processing to receive TUIO messages from any TUIO enabled tracker. The 
TuioDemo sketch graphically displays the object and cursor states on the 
screen. You can use this demo as a starting point for the development of 
you own Processing sketch based on the TUIO framework. Please refer to 
the source code of the example and the following section for further 
details on its usage.

Keep in mind to make your graphics scalable for the varying screen and 
window resolutions. A reasonable TUIO application will run in fullscreen 
mode, although the windowed mode might be useful for debugging purposes 
or working with the TUIO Simulator.

## Installation:

Move the uncompressed **TUIO** folder to the **libraries** directory of 
your Processing sketchbook. In order to use this library, you will need 
to provide the according import statement within the header of your 
Processing sketch:

`import TUIO.*;`

## Application Programming Interface:

First you need to create an instance of the **TuioProcessing** client, 
providing the instance of your sketch to the constructor using the 
**this** argument. The TuioProcessing client immediately starts 
listening to incoming TUIO messages and generates higher level events 
based on the object and cursor movements.

<pre>TuioProcessing tuioClient = new TuioProcessing(this);
</pre>

Therefore your sketch needs to implement the following methods in order 
to be able to receive these TUIO events properly:

*   **addTuioObject(TuioObject tobj)** this is called when an object 
becomes visible
*   **updateTuioObject(TuioObject tobj)** an object was moved on the 
table surface
*   **removeTuioObject(TuioObject tobj)** an object was removed from the 
table
*   **addTuioCursor(TuioCursor tcur)** this is called when a new cursor 
is detected
*   **updateTuioCursor(TuioCursor tcur)** a cursor was moving on the 
table surface
*   **removeTuioCursor(TuioCursor tcur)** a cursor was removed from the 
table
*   **addTuioBlob(TuioBlob tblb)** this is called when a new blob is 
detected
*   **updateTuioBlob(TuioBlob tblb)** a blob was moving on the table 
surface
*   **removeTuioBlob(TuioBlob tblb)** a blob was removed from the table
*   **refresh(TuioTime frameTime)** this method is called after each 
bundle,  
    use it to repaint your screen for example

Each **TuioObject**, **TuioCursor** or **TuioBlob** is identified with a 
unique **SessionID**, which it maintains over its lifetime. Additionally 
each TuioObject carries a **SymbolID** that corresponds to its attached 
fiducial marker number. The **CursorID** of the TuioCursor is always a 
number in the range of all currently detected cursors. You can retrieve 
these ID numbers with the according **getSessionID()**, 
**getSymbolID()** or **getCursorID()** methods.

The TuioObject, TuioCursor and TuioBlob references are updated 
automatically by the TuioProcessing client and are always referencing 
the same instance over the object's or cursor's lifetime. All the 
TuioObject, TuioCursor and TuioBlob attributes are encapsulated, and can 
be accessed with methods such as **getX()**, **getY()** and 
**getAngle()**. There exist further methods for the retrieval of speed 
and acceleration values, please see the provided example sketches for a 
complete list. TuioObject, TuioCursor and TuioBlob also have some 
additional convenience methods for the calculation of distances and 
angles between objects. The **getPath()** method returns an ArrayList of 
TuioPoint representing the movement path of the object. Please refer to 
the [documentation](http://www.tuio.org/?java) of the TUIO Java 
reference implementations for further details on all the available 
methods.

Alternatively, the TuioProcessing class contains some methods for the 
polling of the current object and cursor states. There are methods which 
return either a list or individual TuioObject, TuioCursor and TuioBlob 
objects.

*   **getTuioObjectList()** returns an ArrayList of all currently 
present TuioObjects
*   **getTuioCursorList()** returns an ArrayList of all currently 
present TuioCursors
*   **getTuioBlobList()** returns an ArrayList of all currently present 
TuioBlobs
*   **getTuioObject(long s_id)** returns a TuioObject (or NULL if not 
present)
*   **getTuioCursor(long s_id)** returns a TuioCursor (or NULL if not 
present)
*   **getTuioBlob(long s_id)** returns a TuioBlob (or NULL if not 
present)

## License:

This library is free software; you can redistribute it and/or modify it 
under the terms of the GNU Lesser General Public License as published by 
the Free Software Foundation; either version 3.0 of the License, or (at 
your option) any later version.

This library is distributed in the hope that it will be useful, but 
WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser 
General Public License for more details.

You should have received a copy of the GNU Lesser General Public License 
along with this library; if not, write to the Free Software Foundation, 
Inc., 59 Temple Place, Suite 330, Boston, MA02111-1307USA

## References:

This Processing library is based on the [TUIO Java](http://www.tuio.org/?java) reference implementation, which 
includes the [JavaOSC](http://www.illposed.com/software/javaosc.html) 
OpenSound Control library.
