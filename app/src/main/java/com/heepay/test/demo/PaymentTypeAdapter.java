package com.heepay.test.demo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class PaymentTypeAdapter extends RecyclerView.Adapter<PaymentTypeHolder> {

    private String[] mPaymentType;
    private int[] mPaymentIcon;
    private OnItemClickListener mItemClickListener;

    public PaymentTypeAdapter(String[] paymentType, int[] paymentIcon) {
        this.mPaymentType = paymentType;
        this.mPaymentIcon = paymentIcon;
    }

    @Override
    public PaymentTypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return new PaymentTypeHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentTypeHolder holder, int position) {
        holder.ivPayment.setImageResource(mPaymentIcon[position]);
        holder.tvPayment.setText(mPaymentType[position]);
        final int itemPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(itemPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPaymentType.length;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
