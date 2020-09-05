package com.abdiel.abdiel11rpl012018;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DatakuViewHolder> {
    private ArrayList<Model> datalist;
    private Callback callback;
    View viewku;
    int posku;

    interface Callback {
        void onClick(int position);
        void test();

    }
    public DataAdapter(ArrayList<Model>datalist, Callback callback){
        this.callback = callback;
        this.datalist = datalist;
        Log.d("makanan","MahasiswaAdapter: "+datalist.size()+"");
    }
    @NonNull
    @Override
    public DatakuViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( parent.getContext() );
        View view = inflater.inflate( R.layout.adapterrv, parent , false );
        return new DatakuViewHolder( view );
    }

    @Override
    public void onBindViewHolder(final DatakuViewHolder holder, int position) {
        holder.txtnama.setText( datalist.get( position ).getOriginal_title());
        holder.txtNpm.setText( datalist.get( position ).getOverview() );
        Log.d( "makananku","onBindViewHolder: "+datalist.get( position ).poster_path );
        Glide.with( holder.itemView )
                .load( datalist.get( position ).getPoster_path() )
                .override( Target.SIZE_ORIGINAL )
                .placeholder( R.mipmap.ic_launcher )
                .into( holder.ivprofile );

    }

    @Override
    public int getItemCount() {
        return (datalist != null)? datalist.size() : 0;
    }



    public class DatakuViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        private TextView txtnama, txtNpm;
        CardView card;
        ImageView ivprofile;

        public DatakuViewHolder(@NonNull View itemView) {
            super( itemView );
            viewku=itemView;
            card = (CardView) itemView.findViewById( R.id.cardku );
            ivprofile = (ImageView)itemView.findViewById( R.id.ivprofile );
            txtnama = (TextView)itemView.findViewById( R.id.tvname );
            txtNpm = (TextView)itemView.findViewById( R.id.tvdesc);
            itemView.setOnCreateContextMenuListener( this );

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick( getAdapterPosition() );
                }
            } );

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add( Menu.NONE,1,1,"Edit" );
            MenuItem Delete = menu.add(Menu.NONE,2,2,"Delete");
            posku = getAdapterPosition();
            Edit.setOnMenuItemClickListener(onEditMenu );
            Delete.setOnMenuItemClickListener( onEditMenu );
        }
        private MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case 1:
                        Toast.makeText(viewku.getContext(),""+posku,Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        //Do Stuff
                        break;
                }
                return true;
            }
        };

    }
}