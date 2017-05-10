package iqiyi.com.translatescroll;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button firstBtn;
    private Button secondBtn;
    private int x = 20;
    private int y = 30;
    private boolean isClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        firstBtn = (Button) findViewById(R.id.btn_fist);
        secondBtn = (Button) findViewById(R.id.btn_sec);

        firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ViewGroup.LayoutParams viewGroup = firstBtn.getLayoutParams();

//                x += 10;
//                y += 10;
//                ((View)firstBtn.getParent()).scrollTo(x, y);

                /**
                 * 测试 属性动画的水平滑动 是否会改变 getLeft
                 * result ： getLeft 没变化
                 */
                Toast.makeText(MainActivity.this, "currentGetLeft: " + firstBtn.getLeft(), Toast.LENGTH_SHORT).show();
                if(!isClick){
                    ObjectAnimator.ofFloat(firstBtn, "translationX", 0, 100).setDuration(1000).start();
                    isClick = true;
                }
                Toast.makeText(MainActivity.this, "getLeft: " + firstBtn.getLeft(), Toast.LENGTH_SHORT).show();

                /**
                 * 测试 设置了 setTranslate 属性后  getLeft 的值是否会变化的问题
                 *   result : getLeft 确实没变化
                 */
//                Toast.makeText(MainActivity.this, "currentGetLeft: " + firstBtn.getLeft(), Toast.LENGTH_SHORT).show();
//                firstBtn.setTranslationX(40);
//                Toast.makeText(MainActivity.this, "getLeft: " + firstBtn.getLeft(), Toast.LENGTH_SHORT).show();

                /**
                 * 测试一下 用 layoutParams 的方式  getLeft 是否会变化
                 * result: getLeft 变化了
                 */
//                Toast.makeText(MainActivity.this, "currentGetLeft: " + firstBtn.getLeft(), Toast.LENGTH_SHORT).show();
//
//                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) firstBtn.getLayoutParams();
//                params.leftMargin = 200;
//                firstBtn.setLayoutParams(params);
//                Toast.makeText(MainActivity.this, "getLeft: " + firstBtn.getLeft(), Toast.LENGTH_SHORT).show();

                /**
                 * 测试一下 用 scroll 的方式  getLeft 是否会变化
                 * result : getLeft 没变化
                 */
//                Toast.makeText(MainActivity.this, "currentGetLeft: " + firstBtn.getLeft(), Toast.LENGTH_SHORT).show();
//                ((View) firstBtn.getParent()).scrollTo(200, 300);
//                Toast.makeText(MainActivity.this, "getLeft: " + firstBtn.getLeft(), Toast.LENGTH_SHORT).show();


            }
        });

        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
