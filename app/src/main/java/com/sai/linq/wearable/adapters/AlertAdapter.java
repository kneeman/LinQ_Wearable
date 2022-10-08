package com.sai.linq.wearable.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import com.sai.linq.wearable.R;
import com.sai.linq.wearable.models.AlertItemTableServiceEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by craigknee on 7/25/17.
 */

public class AlertAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AlertItemTableServiceEntity> alertItems;
    private static final int EMPTY_VIEW = 3050;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public AlertAdapter(List<AlertItemTableServiceEntity> alertItems) {
        this.alertItems = alertItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EMPTY_VIEW) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
            return new EmptyViewHolder(v);
        }
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_alert_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        if (alertItems.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == EMPTY_VIEW) {
            return;
        }
        boolean showDurationTimer = true;
        final AlertItemTableServiceEntity alertItem = alertItems.get(position);
        final TextView textView = ((ViewHolder) holder).rowKey;
        final ViewGroup viewGroup = ((ViewHolder) holder).viewGroup;
        Chronometer chronometer = ((ViewHolder) holder).chronometer;

        textView.setText(alertItem.getDeviceType() + " " + alertItem.getRoomID());
//        setProperBackgroundColor(alertItem, viewGroup, textView);


        if (showDurationTimer) {
            chronometer.setVisibility(View.VISIBLE);
        } else {
            chronometer.setVisibility(View.GONE);
        }

        Date alertDate = null;
        //TODO CK handle exception
        try {
            alertDate = simpleDateFormat.parse(alertItem.getLocalTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Chronometer timeElapsed = ((ViewHolder) holder).chronometer;
        timeElapsed.setBase(alertDate.getTime());
        timeElapsed.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long t = System.currentTimeMillis() - timeElapsed.getBase();

                long days = TimeUnit.MILLISECONDS.toDays(t);
                t -= TimeUnit.DAYS.toMillis(days);

                long hours = TimeUnit.MILLISECONDS.toHours(t);
                t -= TimeUnit.HOURS.toMillis(hours);

                long minutes = TimeUnit.MILLISECONDS.toMinutes(t);
                t -= TimeUnit.MINUTES.toMillis(minutes);

                long seconds = TimeUnit.MILLISECONDS.toSeconds(t);

                StringBuilder sb = new StringBuilder();
                if (days > 0) {
                    sb.append(days);
                    sb.append(":");
                }
                if (hours > 0 || sb.length() > 0) {
                    sb.append(hours);
                    sb.append(":");
                }
                if (minutes > 0 || sb.length() > 0) {
                    if (minutes < 10 && sb.length() > 0) {
                        sb.append("0");
                    }
                    sb.append(minutes);
                    sb.append(":");
                }
                if (seconds > 0 || sb.length() > 0) {
                    if (seconds < 10) {
                        sb.append("0");
                    }
                    sb.append(seconds);
                }
                timeElapsed.setText(sb.toString());
//                setProperBackgroundColor(alertItem, viewGroup, textView);
            }
        });
        timeElapsed.start();
    }

    @Override
    public int getItemCount() {
        if (alertItems.size() == 0) {
            return 1;
        } else {
            return alertItems.size();
        }
    }

    public void swap(List<AlertItemTableServiceEntity> listToSwap) {
        if (null != alertItems) {
            alertItems.clear();
            alertItems.addAll(listToSwap);
        } else {
            alertItems = listToSwap;
        }
        notifyDataSetChanged();
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {
        private TextView escalationNotification;

        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView rowKey;
        private Chronometer chronometer;
        private ViewGroup viewGroup;

        ViewHolder(View itemView) {
            super(itemView);
            rowKey = (TextView) itemView.findViewById(R.id.row_key_textView);
            chronometer = (Chronometer) itemView.findViewById(R.id.alert_chronometer);
            viewGroup = (ViewGroup) itemView.findViewById(R.id.alert_item_view_group);
        }
    }

//    private void setProperBackgroundColor(AlertItemTableServiceEntity alertItem, ViewGroup viewGroup, TextView textView) {
//        int secondsElapsed = getSecondsElapsed(alertItem.getLocalTime());
//        if (secondsElapsed > 60) {
//            if (viewGroup.getBackground() != ContextCompat.getDrawable(textView.getContext(), R.drawable.flashing)) {
//                viewGroup.setBackground(ContextCompat.getDrawable(textView.getContext(), R.drawable.flashing));
//                AnimationDrawable animationDrawable = (AnimationDrawable) viewGroup.getBackground();
//                animationDrawable.start();
//            }
//
//        } else if (secondsElapsed > 40) {
//            if (viewGroup.getBackground() != ContextCompat.getDrawable(textView.getContext(), android.R.color.holo_red_dark)) {
//                viewGroup.setBackgroundColor(ContextCompat.getColor(textView.getContext(), android.R.color.holo_red_dark));
//            }
//        } else if (secondsElapsed > 20) {
//            if (viewGroup.getBackground() != ContextCompat.getDrawable(textView.getContext(), R.color.yellow)) {
//                viewGroup.setBackgroundColor(ContextCompat.getColor(textView.getContext(), R.color.yellow));
//            }
//        } else if (viewGroup.getBackground() != ContextCompat.getDrawable(textView.getContext(), android.R.color.holo_green_dark)) {
//            viewGroup.setBackgroundColor(ContextCompat.getColor(textView.getContext(), android.R.color.holo_green_dark));
//        }
//    }

    private int getSecondsElapsed(String alertItemLocalTime) {
        try {
            Date alertDate = simpleDateFormat.parse(alertItemLocalTime);
            Date current = Calendar.getInstance().getTime();
            return (int) ((current.getTime() - alertDate.getTime()) / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
