
# Kotlin
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }
-dontwarn kotlinx.serialization.**
-dontwarn kotlin.metadata.internal.**

# Room
-dontwarn androidx.room.jarjarred.**
-dontwarn org.abego.treelayout.**
-dontwarn com.ibm.icu.**
-keep class * extends androidx.room.RoomDatabase
-keep class * extends androidx.room.Entity
-keep class ru.malygin.anytoany.data.local_database.models.** { *; }
-keepclassmembers class ru.malygin.anytoany.data.local_database.models.* { *;}
-dontwarn ru.malygin.anytoany.data.local_database.models.AppDatabaseConstructor

# google
-dontwarn com.google.j2objc.annotations.**
-dontwarn com.google.common.**

# JetBrains аннотации
-keep class org.jetbrains.annotations.** { *; }
-keep class org.intellij.lang.annotations.** { *; }

# Сохранение ViewModel
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

# Сохранение сериализуемых классов
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-dontwarn java.sql.JDBCType
-dontwarn javax.lang.model.SourceVersion
-dontwarn javax.lang.model.element.Element
-dontwarn javax.lang.model.element.ElementKind
-dontwarn javax.lang.model.element.ElementVisitor
-dontwarn javax.lang.model.element.ExecutableElement
-dontwarn javax.lang.model.element.Modifier
-dontwarn javax.lang.model.element.Name
-dontwarn javax.lang.model.element.PackageElement
-dontwarn javax.lang.model.element.TypeElement
-dontwarn javax.lang.model.element.TypeParameterElement
-dontwarn javax.lang.model.element.VariableElement
-dontwarn javax.lang.model.type.ArrayType
-dontwarn javax.lang.model.type.DeclaredType
-dontwarn javax.lang.model.type.ExecutableType
-dontwarn javax.lang.model.type.TypeKind
-dontwarn javax.lang.model.type.TypeMirror
-dontwarn javax.lang.model.type.TypeVariable
-dontwarn javax.lang.model.type.TypeVisitor
-dontwarn javax.lang.model.util.ElementFilter
-dontwarn javax.lang.model.util.SimpleElementVisitor8
-dontwarn javax.lang.model.util.SimpleTypeVisitor8
-dontwarn javax.lang.model.util.Types