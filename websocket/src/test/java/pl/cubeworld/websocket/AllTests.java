package pl.cubeworld.websocket;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdjustObjectTest.class,
        InvokerTest.class,
        WebSocketControllerScannerTest.class,
        WebSocketControllerDescriptionTest.class
})
public class AllTests {

}
