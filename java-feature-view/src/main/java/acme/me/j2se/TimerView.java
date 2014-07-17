package acme.me.j2se;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerView extends TimerTask{
    public static void main(String[] args){
        Timer timer = new Timer();
        TimerTask task = new TimerView();
        timer.schedule(task, new Date());
        task = new TimerView();
        timer.schedule(task, 5000);
    }

    @Override
    public void run() {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString());
    }
}
