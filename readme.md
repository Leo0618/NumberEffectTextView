#NumberEffectTextView#

##

可实现数字滚动效果的TextView,支持小数，用于账户金钱余额数字显示。

#How to Use#

##Gradle
1. Add this to your build.gradle.
    
    	dependencies {
   	 		compile 'cn.whereyougo.numbereffecttextview:library:1.0.0'
		}

2. use in xml

      	<cn.whereyougo.numbereffevcttextview.NumberEffectTextView
             android:id="@+id/tv_account_total_my"
             android:layout_width="match_parent"
             android:layout_height="wrap_content"
             android:text="0.00"
             android:textColor="@color/white"
             android:textSize="20sp"/>

3. use in code

    	NunberEffectTextView totalMyAccount = (NunberEffectTextView) view.findViewById(R.id.tv_account_total_my);

        totalMyAccount.withNumber(mBean.getMoney()).setDuration(1000).start();

##Maven
	<dependency>
        <groupId>cn.whereyougo.numbereffecttextview</groupId>
        <artifactId>library</artifactId>
        <version>1.0.0</version>
        <type>jar</type>
        <classifier>javadoc</classifier>
	</dependency>