package com.heepay.test.demo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PaymentTypeHolder extends RecyclerView.ViewHolder {

    public ImageView ivPayment;
    public TextView tvPayment;

    public PaymentTypeHolder(View itemView) {
        super(itemView);
        ivPayment = (ImageView) itemView.findViewById(R.id.ivPayment);
        tvPayment = (TextView) itemView.findViewById(R.id.tvPayment);
    }
}
