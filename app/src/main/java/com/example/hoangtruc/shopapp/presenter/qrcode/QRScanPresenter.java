package com.example.hoangtruc.shopapp.presenter.qrcode;

import com.example.hoangtruc.shopapp.data.network.QRcodeResponseData;
import com.example.hoangtruc.shopapp.view.qrcode.BarcodeMvpView;

public class QRScanPresenter implements QRScanMvpPresenter {
    private QRcodeResponseData mQRcodeResponseData;
    private BarcodeMvpView mBarcodeMvpView;

    public QRScanPresenter(BarcodeMvpView barcodeMvpView) {
        mBarcodeMvpView = barcodeMvpView;
        mQRcodeResponseData=new QRcodeResponseData();
    }

    @Override
    public void handleScanQRcode(String qrCode) {
        int idProduct=mQRcodeResponseData.getIDProductByQR(qrCode);
        mBarcodeMvpView.getIdProduct(idProduct);
    }
}
