package cisc.barcamp.passscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class QRScanView extends AppCompatActivity {

    CameraView cvCamera;
    boolean isDetected = false;
    FirebaseVisionBarcodeDetectorOptions options;
    FirebaseVisionBarcodeDetector detector;

    private void loadUI() {
        cvCamera = findViewById(R.id.cvCamera);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan_view);
        loadUI();
        permissions();
    }

    private void permissions() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.INTERNET)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        setupCamera();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        showMessage("Se debe aceptar todos los permisos para el funcinamiento de la APP");
                    }
                }).check();
        setupVision();
    }

    private void setupVision() {
        options = new FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
                .build();
        detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options);
    }

    private void setupCamera() {
        cvCamera.setLifecycleOwner(this);
        cvCamera.addFrameProcessor(frame -> processImage(getVisionImageFromFrame(frame)));
    }

    private FirebaseVisionImage getVisionImageFromFrame(Frame frame) {
        byte[] data = frame.getData();
        FirebaseVisionImageMetadata metadata = new FirebaseVisionImageMetadata.Builder()
                .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                .setHeight(frame.getSize().getHeight())
                .setWidth(frame.getSize().getWidth())
                .build();
        return FirebaseVisionImage.fromByteArray(data, metadata);
    }

    void processImage(FirebaseVisionImage image) {
        if (!isDetected)
            detector.detectInImage(image)
                    .addOnSuccessListener(this::processResult)
                    .addOnFailureListener(this::showMessage);

    }

    void showMessage(Object e) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (e instanceof Exception) {
            Exception error = (Exception) e;
            builder.setMessage(error.getMessage()).setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        } else if (e instanceof String) {
            String message = (String) e;
            builder.setMessage(message).setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void processResult(@NotNull List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
        if (firebaseVisionBarcodes.size() > 0) {
            isDetected = true;
            for (FirebaseVisionBarcode item : firebaseVisionBarcodes) {
                int value_type = item.getValueType();
                if (value_type == FirebaseVisionBarcode.TYPE_TEXT) {
                    processQR(item.getRawValue());
                }
            }
        }

    }

    private void processQR(String rawValue) {
        Intent success = new Intent(this, SuccessQR.class);
        success.putExtra(SuccessQR.DATA, rawValue);
        startActivity(success);
        finish();
        //llamar API antes de llamar ventana de datos
    }

}
