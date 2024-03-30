package main;

import java.util.Collection;

import utils.DataType;
import utils.NullObj;

public enum SystemFunction implements IFunction {
    
    LENGTH() {
        @Override
        public Object eval(Object[] args) throws IllegalArgumentException {
            if (args.length != 1) {
                throw utils.Error.invalidNargsException("len", 1, args.length);
            }
            var obj = args[0];
            if (obj.getClass() == String.class) {
                return Double.valueOf(((String)obj).length());
            }
            if (obj.getClass() == DataType.ARRAY_CLASS) {
                return Double.valueOf(((Object[])obj).length);
            }
            throw utils.Error.invalidArgTypeException("len", Collection.class, obj.getClass());
        }
    },

    RANDOM() {
        @Override
        public Object eval(Object[] args) throws Exception {
            if (args.length != 0) {
                throw utils.Error.invalidNargsException("random", 0, args.length);
            }
            return Double.valueOf(Math.random());
        }
        
    }
}
