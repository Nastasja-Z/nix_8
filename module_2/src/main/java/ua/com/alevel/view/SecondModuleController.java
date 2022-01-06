package ua.com.alevel.view;
import ua.com.alevel.firstuniquename.FirstUniqueName;
import ua.com.alevel.stringofdata.StringOfData;
import ua.com.alevel.thecheapestway.TheCheapestWay;

public class SecondModuleController {

    public void run() {
        System.out.println("SecondModuleController.run");
        new FirstUniqueName().run();
        new TheCheapestWay().run();
        new StringOfData().run();
    }
}
