package com.luoares.plan;

public class ListInformation implements Cloneable {
    private String plan = "";
    private Boolean finish = false;
    private Integer style = 0;

    public Object clone()
    {
        Object tmp = null;
        try {
            tmp =(ListInformation) super.clone(); //Object 中的clone()识别出你要复制的是哪一个对象。
        } catch(CloneNotSupportedException e) {
            System.out.println(e.toString());
        }
        return tmp;
    }

    public ListInformation() {
    }

    public ListInformation(String plan, Boolean finish, Integer style) {
        this.plan = plan;
        this.finish = finish;
        this.style = style;
    }

    public String getPlan() {
        return plan;
    }
    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Boolean getChecked() {
        return finish;
    }
    public void setChecked(Boolean finish) {
        this.finish = finish;
    }

    public Integer getStyle() {
        return style;
    }
    public void setStyle(Integer style) {
        this.style = style;
    }
}