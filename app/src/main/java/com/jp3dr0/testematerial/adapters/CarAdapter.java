package com.jp3dr0.testematerial.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.jp3dr0.testematerial.Car;
import com.jp3dr0.testematerial.ImageHelper;
import com.jp3dr0.testematerial.MainActivity;
import com.jp3dr0.testematerial.R;
import com.jp3dr0.testematerial.RecyclerAdapter;
import com.jp3dr0.testematerial.RecyclerViewOnClickListenerHack;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder> {

    private MainActivity activity;
    private Context mContext;
    private List<Car> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private float scale;
    private int width;
    private int height;

    private static final int LIST_ITEM = 0;
    private static final int GRID_ITEM = 1;
    private boolean isSwitchView = true;

    public CarAdapter(MainActivity activity, Context c, List<Car> l){
        activity = activity;
        mContext = c;
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        scale = mContext.getResources().getDisplayMetrics().density;
        width = mContext.getResources().getDisplayMetrics().widthPixels - (int)(14*scale + 0.5f);
        height = (width/16) * 9;
    }

    @Override
    public int getItemViewType (int position) {
        if (isSwitchView){
            return LIST_ITEM;
        }else{
            return GRID_ITEM;
        }
    }

    public boolean toggleItemViewType () {
        isSwitchView = !isSwitchView;
        return isSwitchView;
    }

    // Chamado para criar uma nova view
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.i("LOG", "onCreateViewHolder()");
        //View view = LayoutInflater.from(viewGroup.getContext()).inflate(isProductViewAsList ? R.layout.item_car_list : R.layout.item_car_grid, viewGroup, false);
        //View view = mLayoutInflater.inflate(R.layout.item_car, viewGroup, false);
        View view = mLayoutInflater.inflate(R.layout.item_car_card, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(view);
        return mvh;
    }

    // Vincula os dados dos carros a view
    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {

        //myViewHolder.cardView.setCardBackgroundColor(sparseArray.valueAt(i).isSelected() ? Color.LTGRAY : Color.WHITE);

        Log.i("LOG", "onBindViewHolder()");

        myViewHolder.tvModel.setText(mList.get(position).getModel() );
        myViewHolder.tvBrand.setText( mList.get(position).getBrand() );

        myViewHolder.ivFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "quando amanhecer: " + position, Toast.LENGTH_SHORT).show();
                Car c = mList.get(position);
                c.setEhFavorito( !c.isEhFavorito() );
                //activity.updateEhFavoritoMoto( m );
            }
        });

        if( mList.get(position).isEhFavorito() ){
            myViewHolder.ivFavorito.setImageResource( R.drawable.ic_star_black_24dp );
        }
        else{
            myViewHolder.ivFavorito.setImageResource( R.drawable.ic_star_border_black_24dp );
        }

        //myViewHolder.ivCar.setImageResource( mList.get(position).getPhoto() );
        Glide.with(mContext).load(mList.get(position).getPhoto()).apply(new RequestOptions().placeholder(R.drawable.loading)).into(myViewHolder.ivCar);

        /*
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            myViewHolder.ivCar.setImageResource(mList.get(position).getPhoto());
        }
        else{
            Bitmap bitmap = BitmapFactory.decodeResource( mContext.getResources(), mList.get(position).getPhoto());
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

            bitmap = ImageHelper.getRoundedCornerBitmap(mContext, bitmap, 4, width, height, false, false, true, true);
            myViewHolder.ivCar.setImageBitmap(bitmap);
        }
        */

        Bitmap bitmap = BitmapFactory.decodeResource( mContext.getResources(), mList.get(position).getPhoto());
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

        bitmap = ImageHelper.getRoundedCornerBitmap(mContext, bitmap, 4, width, height, false, false, true, true);
        myViewHolder.ivCar.setImageBitmap(bitmap);

        try{
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(myViewHolder.itemView);
        }
        catch(Exception e){

        }
    }

    // Tamanho da lista de carros
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addListItem(Car c, int position){
        mList.add(c);
        notifyItemInserted(position);
    }

    public void removeListItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    // Criando a classe ViewHolder dentro dessa classe aqui (CarAdapter)
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView ivCar;
        private TextView tvModel;
        private TextView tvBrand;
        private ImageView ivFavorito;

        public MyViewHolder(View itemView){
            super(itemView);

            ivCar = (ImageView) itemView.findViewById(R.id.iv_car);
            tvModel = (TextView) itemView.findViewById(R.id.tv_model);
            tvBrand = (TextView) itemView.findViewById(R.id.tv_brand);
            ivFavorito = (ImageView) itemView.findViewById(R.id.iv_favorito);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }
    }
}
