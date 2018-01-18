package com.myself.deployrequester.util;

import com.myself.deployrequester.bo.Module;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
public class ListSorter<T> {
    final public static String ASCENDING = "asc";
    final public static String DESCENDING = "desc";

    public void Sort(List<T> list, final String methodName, final String sortOrder) {
        Collections.sort(list, new Comparator() {
            public int compare(Object a, Object b) {
                int ret = 0;
                try {
                    Method m1 = ((T) a).getClass().getMethod(methodName, null);
                    Method m2 = ((T) b).getClass().getMethod(methodName, null);
                    Object val1 = m1.invoke((T) a);
                    Object val2 = m2.invoke((T) b);
                    if (sortOrder != null && ListSorter.DESCENDING.equals(sortOrder)) {// 倒序
                        if (val1 == null || val2 == null) {
                            ret = 0;
                        } else {
                            if (val1 instanceof Short && val2 instanceof Short) {
                                ret = ((Short) val2).compareTo((Short) val1);
                            } else if (val1 instanceof Integer && val2 instanceof Integer) {
                                ret = ((Integer) val2).compareTo((Integer) val1);
                            } else if (val1 instanceof Float && val2 instanceof Float) {
                                ret = (((Float) val2).compareTo((Float) val1));
                            } else if (val1 instanceof Double && val2 instanceof Double) {
                                ret = (((Double) val2).compareTo((Double) val1));
                            } else if (val1 instanceof String && val2 instanceof String) {
                                ret = ((String) val2).compareTo((String) val1);
                            } else {
                                ret = val2.toString().compareTo(val1.toString());
                            }
                        }
                    } else {
                        // 正序
                        if (val1 == null || val2 == null) {
                            ret = 0;
                        } else {
                            if (val1 instanceof Short && val2 instanceof Short) {
                                ret = ((Short) val1).compareTo((Short) val2);
                            } else if (val1 instanceof Integer && val2 instanceof Integer) {
                                ret = ((Integer) val1).compareTo((Integer) val2);
                            } else if (val1 instanceof Float && val2 instanceof Float) {
                                ret = (((Float) val1).compareTo((Float) val2));
                            } else if (val1 instanceof Double && val2 instanceof Double) {
                                ret = (((Double) val1).compareTo((Double) val2));
                            } else if (val1 instanceof String && val2 instanceof String) {
                                ret = ((String) val1).compareTo((String) val2);
                            } else {
                                ret = val1.toString().compareTo(val2.toString());
                            }
                        }
                    }
                } catch (NoSuchMethodException nsme) {
                    nsme.printStackTrace();
                } catch (IllegalAccessException iae) {
                    iae.printStackTrace();
                } catch (InvocationTargetException ite) {
                    ite.printStackTrace();
                }
                return ret;
            }
        });
    }

    public static void main(String[] args) {
        Module module1 = new Module();
        module1.setId(Short.valueOf("2"));
        module1.setCode("base");

        Module module2 = new Module();
        module2.setId(Short.valueOf("1"));
        module2.setCode("system");

        Module module3 = new Module();
        module3.setId(Short.valueOf("3"));
        module3.setCode("contract");

        List<Module> moduleList = new ArrayList<Module>();
        moduleList.add(module1);
        moduleList.add(module2);
        moduleList.add(module3);

        for (Module module : moduleList) {
            System.out.println(module.getId() + "---" + module.getCode());
        }

        ListSorter<Module> listSorter = new ListSorter<Module>();
        listSorter.Sort(moduleList, "getId", ListSorter.ASCENDING);

        System.out.println("after ascending order....");
        for (Module module : moduleList) {
            System.out.println(module.getId() + "---" + module.getCode());
        }

        listSorter.Sort(moduleList, "getId", ListSorter.DESCENDING);
        System.out.println("after descending order....");
        for (Module module : moduleList) {
            System.out.println(module.getId() + "---" + module.getCode());
        }

    }
}
