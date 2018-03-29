package com.chinamobile.yunweizhushou.ui.answeringMachine;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.bean.UserBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.answeringMachine.bean.AnswerBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chinamobile.yunweizhushou.R.id.tv_down;

/**
 * Created by Administrator on 2018/3/2.
 */

public class AnsweringMachineActivity extends BaseActivity implements GestureDetector.OnGestureListener, View.OnClickListener {

    @BindView(R.id.tv_last)
    TextView tvLast;
    @BindView(tv_down)
    TextView tvDown;
    @BindView(R.id.vf_question)
    ViewFlipper vfQuestion;
    @BindView(R.id.vs_answer)
    ViewStub vsAnswer;
    @BindView(R.id.lt_select)
    LinearLayout ltSelect;
    private List<AnswerBean> mList;
    //1.定义手势检测器对象
    private GestureDetector mGestureDetector;
    //2.定义一个动画数组，用于为ViewFilpper指定切换动画效果。
    Animation[] animations = new Animation[4];
    //3.定义手势两点之间的最小距离
    private final int FLIP_DISTANCE = 50;
    private List<View> vList;
    private boolean isSuccessOfSubmit = false;
    private UserBean muserBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answering_machine);
        ButterKnife.bind(this);
        muserBean = getMyApplication().getUser();
        getHttpQuestionContent(muserBean.getPhone());
        //1.构建手势检测器
        mGestureDetector = new GestureDetector(this, this);
        initEvent();
        //4.初始化Animation数组
        animations[0] = AnimationUtils.loadAnimation(this, R.anim.left_in);
        animations[1] = AnimationUtils.loadAnimation(this, R.anim.left_out);
        animations[2] = AnimationUtils.loadAnimation(this, R.anim.right_in);
        animations[3] = AnimationUtils.loadAnimation(this, R.anim.right_out);

    }

    private void initEvent() {
        getTitleBar().setMiddleText("知识答题");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvLast.setOnClickListener(this);
        tvDown.setOnClickListener(this);
    }

    private void initVF() {
        vList = new ArrayList<>();
        if (1 == mList.size()) {
            tvDown.setText("提交");
        }
        for (int i = 0; i < mList.size(); i++) {
            AnswerBean answerBean = mList.get(i);
            View view = addQuestionView(answerBean, i + 1, mList.size());
            vfQuestion.addView(view);
            vList.add(view);
        }
    }


    private View addQuestionView(AnswerBean answerBean, int num, int totalNum) {
        View view = View.inflate(this, R.layout.activity_answer_list_item, null);
        TextView tvAnswerDbUsername = (TextView) view.findViewById(R.id.tv_answer_db_username);
        TextView tvAnswerDbTablename = (TextView) view.findViewById(R.id.tv_answer_db_tablename);
        TextView tvAnswerDbname = (TextView) view.findViewById(R.id.tv_answer_dbname);
        TextView tvNum = (TextView) view.findViewById(R.id.tv_num);
        tvAnswerDbname.setText(answerBean.getDb_name());
        tvAnswerDbTablename.setText(answerBean.getTable_name());
        tvAnswerDbUsername.setText(answerBean.getOwner());
        tvNum.setText(num + "/" + totalNum);
        return view;
    }

    /*
    * 请求后台题目
    * */
    private void getHttpQuestionContent(String phoneNum) {
        HashMap<String, String> maps = new HashMap<>();
        maps.put("mobile", phoneNum);
        maps.put("action", "getData");
        startTask(HttpRequestEnum.enum_answering_machine_questions, ConstantValueUtil.URL + "AnswerPassManager?", maps);
       /* OkHttpUtils
                .get()
                .url("http://10.78.238.240:9080/outside/list?")
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(AnsweringMachineActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                        vsAnswer.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jo = null;
                        try {
                            jo = new JSONObject(response);
                            Type type1 = new TypeToken<List<AnswerBean>>() {
                            }.getType();
                            mList = new Gson().fromJson(jo.getString("rows"), type1);
                            //如果没有题目
                            if (mList.size() == 0) {
                                Toast.makeText(AnsweringMachineActivity.this, "您暂无题需答!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //往ViewFlipper添加数据
                            initVF();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });*/
    }


    private void submitQuestionContent(String tableName, String comments) {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "submit");
        map.put("table_name", tableName);
        map.put("comments", comments);
        startTask(HttpRequestEnum.enum_answering_machine_questions, ConstantValueUtil.URL + "AnswerPassManager?", map);

      /*  OkHttpUtils
                .get()
                .url("http://10.78.238.240:9080/outside/submit?")
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(AnsweringMachineActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        isSuccessOfSubmit = true;
                    }
                });*/
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_answering_machine_questions:
                if (!responseBean.isSuccess()) {
                    Toast.makeText(AnsweringMachineActivity.this, responseBean.getMSG(), Toast.LENGTH_SHORT).show();
                    ltSelect.setVisibility(View.GONE);
                    vfQuestion.setVisibility(View.GONE);
                    View vsWarn = vsAnswer.inflate();
                    TextView tvAnswerPrompt = (TextView) vsWarn.findViewById(R.id.tv_answer_prompt);
                    tvAnswerPrompt.setText(responseBean.getMSG());
                } else {
                    Type type1 = new TypeToken<List<AnswerBean>>() {
                    }.getType();
                    mList = new Gson().fromJson(responseBean.getDATA(), type1);
                    //如果没有题目
                    if (mList == null) {
                        Toast.makeText(AnsweringMachineActivity.this, "您暂无题需答!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //往ViewFlipper添加数据
                    initVF();
                }
                break;
            case enum_answering_machine_submit:
                isSuccessOfSubmit = true;
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将Activity上的触发的事件交个GestureDetector处理
        return this.mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
        if (e2.getX() - e1.getX() > FLIP_DISTANCE) {
            if (vfQuestion.getDisplayedChild() == 0) {
                vfQuestion.stopFlipping();
                Toast.makeText(AnsweringMachineActivity.this, "第一个题", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                vfQuestion.setInAnimation(animations[2]);
                vfQuestion.setOutAnimation(animations[3]);
                tvDown.setText("下一题");
                vfQuestion.showPrevious();

                return true;
            }
        } else if (e1.getX() - e2.getX() > FLIP_DISTANCE) {
            if (vfQuestion.getDisplayedChild() == mList.size() - 2) {
                tvDown.setText("提交");
            }
            if (vfQuestion.getDisplayedChild() == mList.size() - 1) {
                vfQuestion.stopFlipping();
                return false;
            } else {
                vfQuestion.setInAnimation(animations[0]);
                vfQuestion.setOutAnimation(animations[1]);
                vfQuestion.showNext();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case tv_down:
                if (vfQuestion.getDisplayedChild() == mList.size() - 2) {
                    tvDown.setText("提交");
                }
                if (vfQuestion.getDisplayedChild() == mList.size() - 1) {
                    vfQuestion.stopFlipping();
                    if ("提交".equals(tvDown.getText().toString())) {
                        submitContent();
                        if (isSuccessOfSubmit) {
                            Toast.makeText(AnsweringMachineActivity.this, "提交完成!", Toast.LENGTH_SHORT).show();
                            vsAnswer.setVisibility(View.VISIBLE);
                            ltSelect.setVisibility(View.GONE);
                            vfQuestion.setVisibility(View.GONE);
                        }
                    }
                } else {
                    vfQuestion.setInAnimation(animations[0]);
                    vfQuestion.setOutAnimation(animations[1]);
                    vfQuestion.showNext();
                }
                break;
            case R.id.tv_last:
                if (vfQuestion.getDisplayedChild() == 0) {
                    vfQuestion.stopFlipping();
                    Toast.makeText(AnsweringMachineActivity.this, "第一个题", Toast.LENGTH_SHORT).show();
                } else {
                    vfQuestion.setInAnimation(animations[2]);
                    vfQuestion.setOutAnimation(animations[3]);
                    tvDown.setText("下一题");
                    vfQuestion.showPrevious();

                }
                break;
            default:
                break;
        }
    }

    private void submitContent() {
        for (int i = 0; i < vList.size(); i++) {
            View view = vList.get(i);
            TextView tvAnswerDbTablename = (TextView) view.findViewById(R.id.tv_answer_db_tablename);
            EditText etAnswerContent = (EditText) view.findViewById(R.id.et_answer_content);
            submitQuestionContent(tvAnswerDbTablename.getText().toString(), etAnswerContent.getText().toString());
        }

    }


}


