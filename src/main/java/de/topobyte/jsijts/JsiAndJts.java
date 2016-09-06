// Copyright 2016 Sebastian Kuerten
//
// This file is part of jsi-and-jts.
//
// jsi-and-jts is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// jsi-and-jts is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with jsi-and-jts. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.jsijts;

import java.util.ArrayList;
import java.util.List;

import com.infomatiq.jsi.Rectangle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineSegment;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;

import de.topobyte.jts.utils.JtsHelper;

/**
 * Conversion methods from JSI to JTS and vice versa.
 * 
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 */
public class JsiAndJts
{

	/**
	 * Convert a Rectangle to an Envelope
	 * 
	 * @param envelope
	 *            the envelope to convert to a rectangle.
	 * @return the bounding box as a Rectangle.
	 */
	public static Rectangle toRectangle(Envelope envelope)
	{
		return new Rectangle((float) envelope.getMinX(),
				(float) envelope.getMinY(), (float) envelope.getMaxX(),
				(float) envelope.getMaxY());
	}

	/**
	 * Convert an Envelope to a Rectangle
	 * 
	 * @param rectangle
	 *            the rectangle to convert to an envelope.
	 * @return the bounding box as an Envelope.
	 */
	public static Envelope toEnvelope(Rectangle rectangle)
	{
		return new Envelope(rectangle.minX, rectangle.maxX, rectangle.minY,
				rectangle.maxY);
	}

	/**
	 * Retrieve a geometry's bounding box as a Rectangle.
	 * 
	 * @param geometry
	 *            the geometry to get the bounding box of.
	 * @return the bounding box as a Rectangle.
	 */
	public static Rectangle toRectangle(Geometry geometry)
	{
		Envelope envelope = geometry.getEnvelopeInternal();
		return new Rectangle((float) envelope.getMinX(),
				(float) envelope.getMinY(), (float) envelope.getMaxX(),
				(float) envelope.getMaxY());
	}

	/**
	 * Retrieve a line segment's bounding box as a Rectangle.
	 * 
	 * @param segment
	 *            the segment to get the bounding box of.
	 * @return the bounding box as a Rectangle.
	 */
	public static Rectangle toRectangle(LineSegment segment)
	{
		Coordinate p = segment.p0;
		Coordinate q = segment.p1;
		double minX = p.x < q.x ? p.x : q.x;
		double maxX = p.x > q.x ? p.x : q.x;
		double minY = p.y < q.y ? p.y : q.y;
		double maxY = p.y > q.y ? p.y : q.y;
		return new Rectangle((float) minX, (float) minY, (float) maxX,
				(float) maxY);
	}

	/**
	 * Convert a rectangle to a polygon.
	 * 
	 * @param rectangle
	 *            the rectangle to convert.
	 * @return the resulting polygon.
	 */
	public static Polygon toGeometry(Rectangle rectangle)
	{
		List<Double> xs = new ArrayList<>(4);
		List<Double> ys = new ArrayList<>(4);
		xs.add((double) rectangle.minX);
		xs.add((double) rectangle.maxX);
		xs.add((double) rectangle.maxX);
		xs.add((double) rectangle.minX);
		ys.add((double) rectangle.minY);
		ys.add((double) rectangle.minY);
		ys.add((double) rectangle.maxY);
		ys.add((double) rectangle.maxY);
		LinearRing ring = JtsHelper.toLinearRing(xs, ys, false);
		return ring.getFactory().createPolygon(ring, null);
	}

}
