package cn.jimmiez.sample.ui;

import cn.jimmiez.pcu.io.PlyReader;
import cn.jimmiez.pcu.model.PcuPointCloud3f;
import cn.jimmiez.pcu.util.PcuArrayUtil;
import cn.jimmiez.pcu.util.PcuCommonUtil;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Point3d;
import java.awt.*;
import java.io.File;
import java.util.List;

public class PointCloudFrame extends JFrame {

    public PointCloudFrame(File plyFile) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(new Dimension((int)(screenSize.height * 0.75), (int)(screenSize.height * 0.75)));
        List<float[]> points = new PlyReader().readPointCloud(plyFile, PcuPointCloud3f.class).getPoint3ds();
        add(new MainCanvas(createScene(points)));
        setVisible(true);
    }

    private BranchGroup createScene(List<float[]> points) {
        BranchGroup bg = new BranchGroup();
        if (points.size() < 1) return bg;
        Shape3D shape = new Shape3D();
        shape.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
        PointArray pa = new PointArray(points.size(), PointArray.COORDINATES);
        Point3d[] pointArray = new Point3d[points.size()];
        for (int i = 0; i < points.size(); i ++) {
            float[] fs = points.get(i);
            pointArray[i] = new Point3d(fs[0], fs[1], fs[2]);
        }
        pa.setCoordinates(0, pointArray);
        shape.setGeometry(pa);
        Appearance ap = new Appearance();

        ColoringAttributes ca = new ColoringAttributes();

        ap.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_READ);
        ca.setColor(.5f, .5f, .5f);

        ap.setColoringAttributes(ca);
        ap.setMaterial(null);
        ap.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_POINT, PolygonAttributes.CULL_BACK, 0));
        ap.setPointAttributes(new PointAttributes(3, false));
        shape.setAppearance(ap);

        bg.addChild(shape);
        return bg;
    }
}
