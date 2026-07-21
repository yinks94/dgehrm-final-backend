package kr.ink94.subject.entity;

/**
 * SubjectKind
 */
public enum SubjectKind {
    교과(10, "일반교과"),
    전문교과(20, "전문교과"),
    비교과(30, "비교과"),
    특수(40, "특수");

    private int id = 0;
    private String displayName;

    SubjectKind(int id, String displayName){
        this.id = id;
        this.displayName = displayName;
    }

    public int getId(){
        return id;
    }

    public String getValue(){
        return name();
    }

    public String getDisplayName(){
        return displayName;
    }
}
