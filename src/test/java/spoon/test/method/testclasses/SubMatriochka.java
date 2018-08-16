package spoon.test.method.testclasses;

import spoon.reflect.factory.Factory;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 16/08/18
 */
public class SubMatriochka {

    public <F extends Enum<F> & Factory> int aMethod(Class<F> object) {
        return 23;
    }

}
