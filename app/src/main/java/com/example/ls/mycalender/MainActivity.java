package com.example.ls.mycalender;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.codbking.calendar.CaledarAdapter;
import com.codbking.calendar.CalendarBean;
import com.codbking.calendar.CalendarDateView;
import com.codbking.calendar.CalendarUtil;
import com.codbking.calendar.CalendarView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.ls.mycalender.Utils.px;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.calendarDateView)
    CalendarDateView calendarDateView;
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    private int scrollview_page;
    private TextView circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        calendarDateView.setAdapter(new CaledarAdapter() {
            @Override
            public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
                TextView view;
                if (convertView == null) {
                    convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.item_calendar, null);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(px(48), px(48));
                    convertView.setLayoutParams(params);
                }
                //TODO 1布局 item_calendar 进入里面去设置自己展示文字样式   自己的什么小红点位置之类的。

                view = (TextView) convertView.findViewById(R.id.text);
                circle= (TextView) convertView.findViewById(R.id.circlae);
                view.setText("" + bean.day);

                //TODO 2 小红点   这里我自己瞎几把判断的天来判断小红点是否显示。你自己根据需求去显示把
                if(bean.day==1||bean.day==10||bean.day==6){
                    circle.setVisibility(View.VISIBLE);
                }else{
                    circle.setVisibility(View.GONE);
                }
                if (bean.mothFlag != 0) {
                    view.setTextColor(0xff9299a1);
                } else {
                    view.setTextColor(0xffffffff);
                }

                return convertView;
            }
        });
        //TODO 3 日期显示   这里来确定你点击之后显示的年月日。自己搞去。简单。

        calendarDateView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarBean bean) {
                title.setText(bean.year + "/" + getDisPlayNumber(bean.moth) + "/" + getDisPlayNumber(bean.day));
            }
        });

        int[] data = CalendarUtil.getYMD(new Date());
        title.setText(data[0] + "/" + data[1] + "/" + data[2]);

        list.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 100;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(MainActivity.this).inflate(android.R.layout.simple_list_item_1, null);
                }

                TextView textView = (TextView) convertView;
                textView.setText("item" + position);


                return convertView;
            }
        });

        //TODO 4 获取当前月份所在的页面   这里scrollview是一个很大的int行数字23121之类的。因为我们年月开始到现在都多少个月呢？几万个了。所以很大。
        scrollview_page=calendarDateView.getCurrentItem();
    }
    private String getDisPlayNumber(int num) {
        return num < 10 ? "0" + num : "" + num;
    }
    //TODO 我人懒你们自己设置自己的左右点击按钮去切换自己的月份吧。我这里返回键作为月份逐渐减少来展示对应的页面的。右边的你们搞一个scrollview++
    @OnClick(R.id.back)
    public void onClick() {
        scrollview_page--;
        calendarDateView.setCurrentItem(scrollview_page,true);
        //mCalendarDateView.setCurrentItem(scrollview-1);

    }
}
