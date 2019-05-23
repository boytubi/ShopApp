package com.example.hoangtruc.shopapp.view.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.presenter.qrcode.QRScanPresenter;
import com.example.hoangtruc.shopapp.view.DetailProductActivity;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class BarcodeActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener ,BarcodeMvpView {
    BarcodeReader barcodeReader;
      private int mIdProduct;
      private QRScanPresenter mQRScanPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        barcodeReader= (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);
        mQRScanPresenter=new QRScanPresenter(this);

    }

    @Override
    public void onScanned(Barcode barcode) {

        Log.d("onScanned: " , barcode.displayValue);
        mQRScanPresenter.handleScanQRcode(barcode.displayValue);
//        Log.d("id product: " ,mIdProduct+"");
        Intent intent=new Intent(BarcodeActivity.this, DetailProductActivity.class);
        intent.putExtra("IDProduct",mIdProduct);
        startActivity(intent);
        barcodeReader.playBeep();
        finish();
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        finish();
    }

    @Override
    public void getIdProduct(int idProduct) {
        mIdProduct=idProduct;
    }
}
