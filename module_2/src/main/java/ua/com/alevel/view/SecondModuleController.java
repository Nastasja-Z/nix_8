package ua.com.alevel.view;

import ua.com.alevel.fileactions.FileActions;
import ua.com.alevel.fileactions.impl.FileActionsImpl;
import ua.com.alevel.firstuniquename.FirstUniqueName;

import static ua.com.alevel.pathhelper.PathHelper.*;

public class SecondModuleController {

    public void run() {
        System.out.println("SecondModuleController.run");
        new FirstUniqueName().run();
    }
}
