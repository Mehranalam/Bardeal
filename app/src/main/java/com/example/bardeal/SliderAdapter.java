package com.example.bardeal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SliderAdapter extends PagerAdapter {

    private Context context;

    LayoutInflater inflater;


    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] imageItems = {
            R.drawable.bascket ,
            R.drawable.feet ,
            R.drawable.carnoval
    };

    public String[] titleItems = {
            "Free Delivery" ,
            "Usable" ,
            "fantastic"
    };

    public String[] descriptionItems = {
            "this onlineShop very very good free delivery is best fetiure ." ,
            "this suit and sheert in this shop is very usable in every cornavoal .." ,
            "this shop is very very fantastic shert for every old ..."
    };


    @Override
    public int getCount() {
        return descriptionItems.length;
    }




    @Override
    public Object instantiateItem(ViewGroup container, int position) {
       inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       View view = inflater.inflate(R.layout.itemsofview ,container ,false);

        ImageView imageView = view.findViewById(R.id.introVactor);
        TextView textView = view.findViewById(R.id.titleForIntro);
        TextView description = view.findViewById(R.id.description);

        imageView.setImageResource(imageItems[position]);
        textView.setText(titleItems[position]);
        description.setText(descriptionItems[position]);

        container.addView(view);

        return view;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (ConstraintLayout)object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((ConstraintLayout) object);
    }
}
