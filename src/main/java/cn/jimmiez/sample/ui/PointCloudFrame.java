package cn.jimmiez.sample.ui;

import cn.jimmiez.sample.model.PointCloud;

import javax.media.j3d.*;
import javax.swing.*;
import java.awt.*;

public class PointCloudFrame extends JFrame {

    public PointCloudFrame(PointCloud pointCloud) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(new Dimension((int)(screenSize.height * 0.75), (int)(screenSize.height * 0.75)));
        add(new MainCanvas(createScene(pointCloud)));
        setVisible(true);
    }

    private BranchGroup createScene(PointCloud pointCloud) {
        BranchGroup bg = new BranchGroup();
        bg.addChild(pointCloud.branchGroup());
        return bg;
    }
}
