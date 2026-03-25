package com.example.macchanger;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/example/macchanger/MacHistoryDao;", "", "getAll", "", "Lcom/example/macchanger/MacEntry;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLast", "insert", "", "entry", "(Lcom/example/macchanger/MacEntry;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
@androidx.room.Dao()
public abstract interface MacHistoryDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.example.macchanger.MacEntry entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM mac_history ORDER BY timestamp DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.macchanger.MacEntry>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM mac_history ORDER BY timestamp DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLast(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.macchanger.MacEntry> $completion);
}