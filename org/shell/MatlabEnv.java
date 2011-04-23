package org.shell;

import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.tools.shell.Global;
import org.jmatrices.dbl.syntax.MatlabSyntax;
import org.jmatrices.dbl.Matrix;

import java.io.PrintStream;


/**
 * MatlabEnv
 * <p>Author: purangp</p>
 * Date: 01.05.2004
 * Time: 00:01:39
 */
public class MatlabEnv extends ScriptableObject {
    public MatlabEnv(ScriptableObject scope) {
        setParentScope(scope);
    }

    public String getClassName() {
        return "MatlabEnv";
    }

    public static String[] getFunctionNames() {
        return new String[]{"create","mlHelp"};
    }

    public static Object create(Context cx, Scriptable thisObj,
                               Object[] args, Function funObj) {
        //PrintStream out = getInstance(thisObj).getOut();
        String s = null;
        try {
            s = Context.toString(args[0]);
        } catch (Exception e) {
            Context.reportRuntimeError("'create' requires a string parameter!");
        }
        try {
            Matrix m =  MatlabSyntax.create(s);
            //out.println(m);
            return m;
        } catch (Exception e) {
            Context.reportRuntimeError(e.getMessage());
        }

        return Context.getUndefinedValue();
    }

    public static void mlHelp(Context cx, Scriptable thisObj,
                               Object[] args, Function funObj) {
        PrintStream out = getInstance(thisObj).getOut();
        out.println(MatlabSyntax.help());
    }


    public static Global getInstance(Scriptable scope)
    {
        scope = ScriptableObject.getTopLevelScope(scope);
        do {
            if (scope instanceof Global) {
                return (Global)scope;
            }
            scope = scope.getPrototype();
        } while (scope != null);

        return null;
    }




}

