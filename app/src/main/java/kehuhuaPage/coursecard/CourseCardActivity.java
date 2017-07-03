package kehuhuaPage.coursecard;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.tubb.calendarselector.library.FullDay;
import com.tubb.calendarselector.library.MonthView;
import com.tubb.calendarselector.library.SCDateUtils;
import com.tubb.calendarselector.library.SCMonth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import razerdp.friendcircle.R;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class CourseCardActivity extends AppCompatActivity implements Protocol {

    private static final String TAG = "CourseCardActivity";

    private TextView tvMonthTitle;
    private ViewPager vpMonth;
    private List<SCMonth> months;
    private static List<String> noAttentDays;//没有上课天
    private static List<String> attentDays;//已上课天
    private static List<String> leaveAttentDays;//请假天
    private int currentPage, mYear, mMonth, startYear = 2000, endYear = 2050;//当前页、年、月


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursecard);

        tvMonthTitle = (TextView) findViewById(R.id.tvMonthTitle);
        vpMonth = (ViewPager) findViewById(R.id.vpMonth);


        months = SCDateUtils.generateMonths(startYear, 1, endYear, 12, SCMonth.SUNDAY_OF_WEEK);
        Log.e(TAG, "onCreate: months " + months.size());
        //获取当前月份
        Calendar c = Calendar.getInstance();//
        mYear = c.get(Calendar.YEAR); // 获取当前年份
        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        currentPage = (mYear - startYear) * 12 + mMonth - 1;

        tvMonthTitle.setText(months.get(currentPage).toString());
        tvMonthTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(CourseCardActivity.this, 0, listener, mYear, mMonth - 1, 1);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
            }
        });

        noAttentDays = new ArrayList<>();
        noAttentDays.add("20170705");
        noAttentDays.add("20170715");
        noAttentDays.add("20170805");
        noAttentDays.add("20170815");
        attentDays = new ArrayList<>();
        attentDays.add("20170707");
        attentDays.add("20170717");
        attentDays.add("20170907");
        attentDays.add("20170817");
        leaveAttentDays = new ArrayList<>();
        leaveAttentDays.add("20170710");
        leaveAttentDays.add("20170720");
        leaveAttentDays.add("20170910");
        leaveAttentDays.add("20171020");


        List<Fragment> fragments = new ArrayList<>(months.size());
        for (SCMonth month : months) {
            fragments.add(MonthFragment.newInstance(month));
        }
        vpMonth.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                SCMonth month = months.get(position);
                tvMonthTitle.setText(month.toString());
            }
        });
        vpMonth.setAdapter(new MonthFragmentAdapter(getSupportFragmentManager(), fragments));
        vpMonth.setCurrentItem(currentPage);
    }

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            //month 初始值0
            currentPage = (year - startYear) * 12 + month;
            tvMonthTitle.setText(months.get(currentPage).toString());
            vpMonth.setCurrentItem(currentPage);
       }

    };

    @Override
    public void clickNextMonthDay(SCMonth currentMonth) {
        int currentIndex = months.indexOf(currentMonth);
        if (currentIndex + 1 < months.size())
            vpMonth.setCurrentItem(currentIndex + 1, true);
        else Toast.makeText(this, "the end month", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clickPrevMonthDay(SCMonth currentMonth) {
        int currentIndex = months.indexOf(currentMonth);
        if (currentIndex - 1 >= 0)
            vpMonth.setCurrentItem(currentIndex - 1, true);
        else Toast.makeText(this, "the start month", Toast.LENGTH_SHORT).show();
    }

    class MonthFragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragments;

        public MonthFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    public static final class MonthFragment extends Fragment {

        public static Fragment newInstance(SCMonth month) {
            Fragment fragment = new MonthFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("month", month);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activity_coursecard_month, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            final MonthView monthView = (MonthView) view.findViewById(R.id.scMv);
            final SCMonth month = getArguments().getParcelable("month");
            Log.e(TAG, "onViewCreated: noAttentDays " + noAttentDays.toString());
            monthView.setSCMonth(month, noAttentDays, attentDays, leaveAttentDays);


            monthView.setMonthDayClickListener(new MonthView.OnMonthDayClickListener() {
                @Override
                public void onMonthDayClick(FullDay day) {
                    if (SCDateUtils.isPrevMonthDay(monthView.getYear(), monthView.getMonth(),
                            day.getYear(), day.getMonth())) {
                        clickPrevMonthDay(month);
                    } else if (SCDateUtils.isNextMonthDay(monthView.getYear(), monthView.getMonth(),
                            day.getYear(), day.getMonth())) {
                        clickNextMonthDay(month);
                    } else {
//                        monthView.clearSelectedDays();
//                        monthView.addSelectedDay(day);
                    }
                }
            });
        }

        private void clickNextMonthDay(SCMonth month) {
            Activity activity = getActivity();
            if (activity instanceof Protocol) {
                Protocol protocol = (Protocol) activity;
                protocol.clickNextMonthDay(month);
            }
        }

        private void clickPrevMonthDay(SCMonth month) {
            Activity activity = getActivity();
            if (activity instanceof Protocol) {
                Protocol protocol = (Protocol) activity;
                protocol.clickPrevMonthDay(month);
            }
        }
    }

}

