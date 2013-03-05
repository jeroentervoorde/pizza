import com.yourkit.probes.ClassRef;
import com.yourkit.probes.MethodName;
import com.yourkit.probes.MethodPattern;
import com.yourkit.probes.OnEnterResult;
import com.yourkit.probes.builtin.Messages;

/**
 *
 * @author jtervoorde
 */
@MethodPattern("org.agoncal.application.*:*(*)")
public class LongRunningMethodProbe {
    
    public static long onEnter() {
        return System.currentTimeMillis();
    }
    
    public static void onReturn(@ClassRef Class clazz, @MethodName String method, @OnEnterResult long start) {
        long end = System.currentTimeMillis();
        
        if (end - start > 100) {
            Messages.message("Performance", String.format("%s.%s took more than 100msec", clazz.getCanonicalName(), method ), String.format("Method took %d msec", end - start));
        }
    }
    
}
