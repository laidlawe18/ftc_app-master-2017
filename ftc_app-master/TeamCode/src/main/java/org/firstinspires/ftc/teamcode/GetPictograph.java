package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.vuforia.Frame;
import com.vuforia.Image;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by BHS-Lab on 12/27/2017.
 */

public class GetPictograph extends AutoAction {

    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;

    int cameraMonitorViewId;
    VuforiaLocalizer.Parameters parameters;
    VuforiaTrackables relicTrackables;
    VuforiaTrackable relicTemplate;

    public GetPictograph(AutonomousOpMode opmode) {
        super(opmode);
    }

    @Override
    public void init() {
        super.init();
        cameraMonitorViewId = opmode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opmode.hardwareMap.appContext.getPackageName());
        parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AddnqqH/////AAAAGdrqb6+wvkw5hrGHA820+J8s0XGluTdYT3eBOwLCrMn4bf8Dby7ZiyLSnO1ZL9Ex4ajoGGtxXdYep/o/kBCloKZKs6inGDjnFbCfi2lJKayJR3B06HPphlLod6F61sQVIg3RHEZc4B1QWAfw4yTSgRc8yZ113XLSVtZ4u+qPPbf1fiWQsEsBu0SbHg6fBHbhS3kc3JVbtXSxPF+NajL8vYEkgDEjmlpGrNwYFzW0P2T0akWx/DLknoJte64/U3JeDF+9zq/n6nWPJAKe+SIINz4DHwr5M7v5aylqh0CCvUHgvGbCuVZxTukoVTMF+IhiYNbVy5rlvBaW/e5TwNYbfPiWvT0b3xA9fDj259Qgreci";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        relicTrackables.activate();
    }

    @Override
    public void update() {
        super.update();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                /* Found an instance of the template. In the actual game, you will probably
                 * loop until this condition occurs, then move on to act accordingly depending
                 * on which VuMark was visible. */
            opmode.addData("column", vuMark.toString());
            done = true;


            /*VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take();
            while (frame == null) {
                frame = vuforia.getFrameQueue().poll();
            }
            Image image = frame.getImage(0);
            Bitmap bm = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.RGB_565);
            bm.copyPixelsFromBuffer(image.getPixels());
            int blue = Color.blue(bm.getPixel(50, 0));

            opmode.addData("blue", blue);*/


                /* For fun, we also exhibit the navigational pose. In the Relic Recovery game,
                 * it is perhaps unlikely that you will actually need to act on this pose information, but
                 * we illustrate it nevertheless, for completeness. */
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();

                /* We further illustrate how to decompose the pose into useful rotational and
                 * translational components */
            if (pose != null) {
                VectorF trans = pose.getTranslation();
                Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                // Extract the X, Y, and Z components of the offset of the target relative to the robot
                double tX = trans.get(0);
                double tY = trans.get(1);
                double tZ = trans.get(2);

                // Extract the rotational components of the target relative to the robot
                double rX = rot.firstAngle;
                double rY = rot.secondAngle;
                double rZ = rot.thirdAngle;
            }
        }
    }

    @Override
    public void end() {
        super.end();
        opmode.telemetry.addData("column", opmode.getDataString("column"));
        opmode.telemetry.addLine(opmode.getDataString("column"));
    }
}
