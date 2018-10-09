package com.example.abdialam.marketresto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.models.Detailpesan;
import com.example.abdialam.marketresto.models.Pesan;
import com.example.abdialam.marketresto.utils.DateHelper;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context mContext;
    private List<Pesan> pesanList;
    private List<Detailpesan> detailpesanList;

    public OrderAdapter(Context ctx, List<Pesan> pesanList){
        this.pesanList = pesanList;
        this.mContext = ctx;
    }


    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_list_order,parent,false);
        OrderAdapter.OrderViewHolder holder = new OrderAdapter.OrderViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        final Pesan pesan = pesanList.get(position);
        holder.mNoOrder.setText("Order :"+" #"+pesan.getIdPesan());
        holder.mNamaResto.setText(pesan.getRestoranNama());
        String status = pesan.getPesanStatus().substring(0,1).toUpperCase() + pesan.getPesanStatus().substring(1).toLowerCase();
        holder.mStatus.setText(status);
        holder.mTanggal.setText(DateHelper.getGridDate(mContext,timeStringToMilis(pesan.getPesanWaktu())));
        detailpesanList = pesan.getDetailpesan();
        double total = 0;
        for (int i = 0; i < detailpesanList.size() ; i++) {
            total += Double.parseDouble(detailpesanList.get(i).getHarga()) * detailpesanList.get(i).getQty();
        }

        holder.mTotal.setText(kursIndonesia(total+pesan.getPesanBiayaAntar()));

        holder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return pesanList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

       @BindView(R.id.tvNamaResto)
        TextView mNamaResto;
       @BindView(R.id.tvStatus)
       TextView mStatus;
       @BindView(R.id.tvWaktu)
       TextView mTanggal;
       @BindView(R.id.parentLayout)
        LinearLayout mParentLayout;
       @BindView(R.id.tvTotal)
       TextView mTotal;
       @BindView(R.id.tvNoOrder)
       TextView mNoOrder;

        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    //convert time string to milis date
    public long timeStringToMilis (String strDate ){

        SimpleDateFormat tgl = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long milliseconds = 0;
        Date date = null;
        try {
            date = tgl.parse(strDate);
            milliseconds = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }

    public String kursIndonesia(double nominal){
        Locale localeID = new Locale("in","ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        String idnNominal = formatRupiah.format(nominal);
        return idnNominal;


    }
}
