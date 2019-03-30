package com.example.abdialam.marketresto.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.activities.DetailOrderActivity;
import com.example.abdialam.marketresto.config.ServerConfig;
import com.example.abdialam.marketresto.models.DetailOrder;
import com.example.abdialam.marketresto.models.Menu;
import com.example.abdialam.marketresto.models.Order;
import com.example.abdialam.marketresto.responses.ResponseValue;
import com.example.abdialam.marketresto.rest.ApiService;
import com.example.abdialam.marketresto.utils.DateHelper;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private Context mContext;
    private List<Order> pesanList;
    private List<Menu> detailpesanList;
    ApiService mApiService;


    public HistoryAdapter(Context ctx, List<Order> pesanList) {
        this.pesanList = pesanList;
        this.mContext = ctx;
    }


    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_list_order, parent, false);
        HistoryAdapter.HistoryViewHolder holder = new HistoryAdapter.HistoryViewHolder(view);
        mApiService = ServerConfig.getAPIService();
        return holder;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        final Order pesan = pesanList.get(position);
        holder.mNoOrder.setText("Order :" + " #" + pesan.getId());
        holder.mNamaResto.setText(pesan.getOrderRestoran());
        // String status = pesan.getPesanStatus().substring(0,1).toUpperCase() + pesan.getPesanStatus().substring(1).toLowerCase();
        holder.mStatus.setText(pesan.getOrderStatus());
        holder.mTanggal.setText(pesan.getCreatedAt().substring(0, 16));
        detailpesanList = pesan.getDetailOrder();
        double total = 0;
        for (int i = 0; i < detailpesanList.size(); i++) {
            total += Double.parseDouble(detailpesanList.get(i).getPivot().getHarga()) * detailpesanList.get(i).getPivot().getQty();
        }

        holder.mTotal.setText(kursIndonesia(total + Double.parseDouble(pesan.getOrderBiayaAnatar())));


        holder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Anda memilih " + pesan.getOrderKonsumen(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, DetailOrderActivity.class);
                intent.putExtra("pesan", (Serializable) pesan);

                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return pesanList.size();
    }


    //convert time string to milis date
    public long timeStringToMilis(String strDate) {

        SimpleDateFormat tgl = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
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

    public String kursIndonesia(double nominal) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        String idnNominal = formatRupiah.format(nominal);
        return idnNominal;
    }


    public class HistoryViewHolder extends RecyclerView.ViewHolder {
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


        public HistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
