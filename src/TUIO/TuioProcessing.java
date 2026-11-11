/*
	TUIO Processing library
	Copyright (c) 2005-2026 Martin Kaltenbrunner <martin@tuio.org>
 
	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 3.0 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
	Lesser General Public License for more details.
	 
	You should have received a copy of the GNU Lesser General Public
	License along with this library.
*/

package TUIO;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import processing.core.PApplet;

public class TuioProcessing implements TuioListener {

	public static final int DEFAULT_PORT = 3333;

	private final PApplet parent;
	private final TuioClient client;

	private final Method refresh;
	private final Method addTuioObject, updateTuioObject, removeTuioObject;
	private final Method addTuioCursor, updateTuioCursor, removeTuioCursor;
	private final Method addTuioBlob, updateTuioBlob, removeTuioBlob;

	public TuioProcessing(PApplet parent) {
		this(parent, DEFAULT_PORT);
	}

	public TuioProcessing(PApplet parent, int port) {
		this(parent, new UdpReceiver(port));
	}

	public TuioProcessing(PApplet parent, int port, String host) {
		this(parent, new UdpReceiver(port, resolveAddress(host)));
	}

	public TuioProcessing(PApplet parent, UdpReceiver receiver) {
		this.parent = parent;
		parent.registerMethod("dispose",this);

		refresh = callback("refresh", TuioTime.class);
		addTuioObject = callback("addTuioObject", TuioObject.class);
		updateTuioObject = callback("updateTuioObject", TuioObject.class);
		removeTuioObject = callback("removeTuioObject", TuioObject.class);
		addTuioCursor = callback("addTuioCursor", TuioCursor.class);
		updateTuioCursor = callback("updateTuioCursor", TuioCursor.class);
		removeTuioCursor = callback("removeTuioCursor", TuioCursor.class);
		addTuioBlob = callback("addTuioBlob", TuioBlob.class);
		updateTuioBlob = callback("updateTuioBlob", TuioBlob.class);
		removeTuioBlob = callback("removeTuioBlob", TuioBlob.class);

		client = new TuioClient(receiver);
		client.addTuioListener(this);
		client.connect();
	}

	private Method callback(String name, Class<?> type) {
		try {
			return parent.getClass().getMethod(name, type);
		} catch (NoSuchMethodException | SecurityException e) {
			System.out.println("TUIO: missing or wrong '"+name+"("+type.getSimpleName()+")' method implementation");
			return null;
		}
	}

	private void invoke(Method method, Object arg) {
		if (method==null) return;
		try {
			method.invoke(parent, arg);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {}
	}

	private static InetAddress resolveAddress(String host) {
		if (host==null || host.isEmpty()) return null; // listen on all interfaces
		try {
			return InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			System.err.println("TUIO: unknown host '"+host+"', listening on the loopback interface only");
			return InetAddress.getLoopbackAddress();
		}
	}

	public void addTuioObject(TuioObject tobj) {
		invoke(addTuioObject, tobj);
	}

	public void updateTuioObject(TuioObject tobj) {
		invoke(updateTuioObject, tobj);
	}

	public void removeTuioObject(TuioObject tobj) {
		invoke(removeTuioObject, tobj);
	}

	public void addTuioCursor(TuioCursor tcur) {
		invoke(addTuioCursor, tcur);
	}

	public void updateTuioCursor(TuioCursor tcur) {
		invoke(updateTuioCursor, tcur);
	}

	public void removeTuioCursor(TuioCursor tcur) {
		invoke(removeTuioCursor, tcur);
	}

	public void addTuioBlob(TuioBlob tblb) {
		invoke(addTuioBlob, tblb);
	}

	public void updateTuioBlob(TuioBlob tblb) {
		invoke(updateTuioBlob, tblb);
	}

	public void removeTuioBlob(TuioBlob tblb) {
		invoke(removeTuioBlob, tblb);
	}

	public void refresh(TuioTime frameTime) {
		invoke(refresh, frameTime);
	}

	public TuioClient getTuioClient() {
		return client;
	}

	public void connect() {
		client.connect();
	}

	public void disconnect() {
		client.disconnect();
	}

	public boolean isConnected() {
		return client.isConnected();
	}

	public ArrayList<TuioObject> getTuioObjectList() {
		return client.getTuioObjectList();
	}

	public ArrayList<TuioCursor> getTuioCursorList() {
		return client.getTuioCursorList();
	}

	public ArrayList<TuioBlob> getTuioBlobList() {
		return client.getTuioBlobList();
	}

	public TuioObject getTuioObject(long s_id) {
		return client.getTuioObject(s_id);
	}

	public TuioCursor getTuioCursor(long s_id) {
		return client.getTuioCursor(s_id);
	}

	public TuioBlob getTuioBlob(long s_id) {
		return client.getTuioBlob(s_id);
	}

	public void stop() {
		if (client.isConnected()) client.disconnect();
	}

	public void dispose() {

		if (client.isConnected()) client.disconnect();

		//this should only be called by PApplet. dispose() is what gets 
		//called when the host applet is stopped, so this should shut down
		//any threads, disconnect from the net, unload memory, etc. 
	}
}
