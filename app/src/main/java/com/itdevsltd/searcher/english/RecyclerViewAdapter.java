package com.itdevsltd.searcher.english;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import android.widget.*;
import android.view.animation.*;
import android.support.v7.widget.*;
import java.util.*;
import android.text.*;
import com.itdevsltd.searcher.R;


/**
 * 
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;

    List<getData> getData;
	private int lastPosition = -1;
	


    public RecyclerViewAdapter(List<getData> getData, Context context){

        super();
        this.getData = getData;
        this.context = context;
    }

	@Override
	public int getItemViewType(int position)
	{
		
		getData getData1 =  getData.get(position);
		
			return 5;
		
		
		
		// TODO: Implement this method
		//return super.getItemViewType(position);
	}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
			ViewHolder viewHolder = new ViewHolder(v);
			return viewHolder;
		
		
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

		
		
        getData getData1 =  getData.get(position);
		
		Viewholder.title.setText(getData1.getTitle());
		
		String serial=getData1.getSerial();
		
		String sub=serial.substring(0,1);
		
		String last3;
		if (serial == null || serial.length() < 3) {
			last3 = serial;
		} else {
			last3 = serial.substring(serial.length() - 3);
		}
		
		serial=sub+"*********"+last3;
		
		
		Viewholder.serial.setText("Serial number :"+serial);
		
		String stat=null;
		String owner=getData1.getOwner();
		if(getData1.getStatus().equals("lost"))
		{
			owner="Person: "+owner;
			stat="<font color='red'>Lost</font>";
		}
		else
		{
			owner="Founder: "+owner;
			
			stat="<font color='green'>Found</font>";
			
		}
		
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
			Viewholder.status.setText(Html.fromHtml(stat,Html.FROM_HTML_MODE_LEGACY));
		} else {
			Viewholder.status.setText(Html.fromHtml(stat));
		}
		
		Viewholder.owner.setText(owner);
		
		if(getData1.getType().equals("property"))
		{
			Viewholder.tp.setImageResource(R.drawable.property);
		}


    }
	
	private void setAnimation(View viewToAnimate, int position, boolean mine)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
			
            Animation animation;
			if(mine==true)
			{
				animation= AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
				viewToAnimate.startAnimation(animation);
				
			}
			else
			{
				animation= AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_top);
				viewToAnimate.startAnimation(animation);
				
			}
			   lastPosition = position;
        }}
	
	
    @Override
    public int getItemCount() {

        return getData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
		
	    public TextView title;
		public TextView status;
		public TextView serial;
		public ImageView tp;
		public TextView owner;
		
        public ViewHolder(View itemView) {

            super(itemView);
		
            title= (TextView) itemView.findViewById(R.id.title) ;
			status= (TextView) itemView.findViewById(R.id.status) ;
			serial= (TextView) itemView.findViewById(R.id.serial) ;
			tp=(ImageView) itemView.findViewById(R.id.tp);
			owner= (TextView) itemView.findViewById(R.id.owner) ;
			
			}
    }
}
