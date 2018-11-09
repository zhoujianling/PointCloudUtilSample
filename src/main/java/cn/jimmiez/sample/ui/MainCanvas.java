package cn.jimmiez.sample.ui;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.Point3d;


public class MainCanvas extends Canvas3D {

    private BoundingSphere boundingSphere = null;

    /**
     * @param contentOnCanvas all you want to draw is the child of this node
     */
    public MainCanvas(BranchGroup contentOnCanvas) {
        super(SimpleUniverse.getPreferredConfiguration());
        // create root branch group
        BranchGroup root = new BranchGroup();
        boundingSphere = getBoundingSphere();

        // create transform group
        TransformGroup rootTG = new TransformGroup();

        rootTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        rootTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        MouseRotate mouseRotate = new MouseRotate(rootTG);
        mouseRotate.setSchedulingBounds(boundingSphere);
        MouseTranslate mouseMove = new MouseTranslate(rootTG);
        mouseMove.setSchedulingBounds(boundingSphere);
        MouseWheelZoom mouseWheelZoom = new MouseWheelZoom(rootTG);
        mouseWheelZoom.setSchedulingBounds(boundingSphere);
        rootTG.addChild(mouseRotate);
        rootTG.addChild(mouseMove);
        rootTG.addChild(mouseWheelZoom);

        //
        contentOnCanvas.compile();

        // root->rootTG->bg
        root.addChild(rootTG);
        rootTG.addChild(contentOnCanvas);

        setupBackground(root);
        // create universe
        SimpleUniverse su = new SimpleUniverse(this);
        // setup the view point
        su.getViewingPlatform().setNominalViewingTransform();
        su.addBranchGraph(root);

    }

    private BoundingSphere getBoundingSphere() {
        return new BoundingSphere(new Point3d(0, 0, 0), 2.0);
    }

    private void setupBackground(BranchGroup root) {
        Background back = new Background(1.0f, 1.0f, 1.0f);
        back.setApplicationBounds(boundingSphere);
        root.addChild(back);
    }

}
