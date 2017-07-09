
import java.util.List;

public interface Operand
{
	String getName();
	boolean isCompound();
	List<? extends Operand> getArgs();
}
