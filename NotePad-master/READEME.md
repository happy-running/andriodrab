# 安卓开发，期中第一题
要在原ListView中显示时间很简单，只需要在查询数据库的时候把修改时间的那一列查出来，然后将其显示即可，但是由于原来数据库中的修改时间这一列的定义为INTEGER,即整型格式，而我们显示时不可能显示这个，因为我们看不懂。所以我们需要把它转化成正常的日期时间格式。这里有两种方法，一是直接修改表的定义字段的类型，将其给位date，然后把所有有关时间戳的操作都改为对日期时间的操作，二是从数据库查询到数据后，将时间戳数据转化为日期时间格式再显示即可，显然第二种更简单，下面采用第二种的方式来显示时间。

* 1、首先找到适配器：
```
SimpleCursorAdapter adapter
= new SimpleCursorAdapter(
    this,                             // The Context for the ListView
    R.layout.noteslist_item,          // 要显示的ListView列表控件
    cursor,                           // The cursor to get items from
    dataColumns,                      
    viewIDs
);
```
为什么先找它？因为它是一个适配器，负责将数据渲染到控件中，找到它就找到了数据和控件之间的关系，看几个重要参数：
> cursor：游标，里面存储着从数据库中查出的各种信息，
> dataColumns：要显示数据列的列名，
> viewIDs：你要将数据渲染到哪个控件，加上控件的id即可。

2、修改相应数据
修改dataColumns:加入要显示的修改时间
```
// 要展示在ListView中的数据（列名）
String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE,
                         NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE} ;
```
修改viewIDS:加入要显示的修改时间的控件id
```
// id为text1的数据就是显示title的控件id（显示上面数据的控件的id）
int[] viewIDs = { android.R.id.text1, R.id.notes_mdftime };
```

至此显示部分的原数据已经完整了，我们知道，这只是显示的数据，最终它还是要从数据库中去取，所有找到查询的函数：
```
Cursor c = query(
    uri,            
    READ_NOTE_PROJECTION,   
    null,                   
    null,                   
    null
    );
```
明显还需要修改一个变量：READ_NOTE_PROJECTION，因为它是查询时的映射字段，这里加上NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE即可，如下：
```
READ_NOTE_PROJECTION = new String[] {
            NotePad.Notes._ID,  
            NotePad.Notes.COLUMN_NAME_NOTE,  
            NotePad.Notes.COLUMN_NAME_TITLE,
            NotePad.Notes.COLUMN_NAME_CREATE_DATE,      // 创建时间
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE // 修改时间
};
```
3、修改布局文件，别忘了布局文件中也应该加入显示时间的控件，这里多加了一个显示图片的控件，是为了以后显示图片用的：
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    android:paddingLeft="6dip"
    android:paddingRight="6dip"
    android:paddingBottom="3dip">
<ImageView
    android:id="@+id/notes_icon"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    />
<TextView
    android:id="@android:id/text1"
    android:layout_width="wrap_content"
    android:layout_height="?android:attr/listPreferredItemHeight"
    android:textSize="30dp"
/>
<TextView
    android:id="@+id/notes_mdftime"
    android:layout_width="match_parent"
    android:layout_height="?android:attr/listPreferredItemHeight"
    android:gravity="right|bottom"
    android:paddingRight="5dip"
    android:singleLine="true"
    />
</LinearLayout>
```
4、将时间戳改为具体的日期时间,我们知道，查询出来的数据是存在Cursor中的，故我们要对Cursor进行过滤，即在数据显示前将整型的时间戳转化为具体的日期时间，此时要用setViewBinder,在其ViewBinder接口中更改cursor数据显示：其中adapter是一个SimpleCursorAdapter对象。

```
// 过滤cursor数据
adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder(){
    public boolean setViewValue(View view, Cursor cursor,
                                int columnIndex) {
        if (view.getId() == R.id.notes_mdftime) {
            //修改我们指定view的数据
            TextView notes_mdftime = (TextView) view;
            String s = stampToDate(Long.toString(cursor.getLong(columnIndex)));
            notes_mdftime.setText(s);
            notes_mdftime.setTextColor(Color.WHITE);
        }
        else {
            TextView notes_mdftime = (TextView) view;
            notes_mdftime.setText(cursor.getString(columnIndex));
        }
        return true;
    }

});
// 将时间戳转化为日期
protected String stampToDate(String stamp) {
    // stamp为时间戳的值
    Long lt = new Long(stamp);
    Date date = new Date(lt);
    // 格式化成日期形式
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    String d = sdf.format(date);
    return d;
}
```
* 解决一个小Bug
由于时间戳直接转化为具体的日期时间，它总是会少8个小时，这是时区问题，可以在时间转化函数加上sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"))，再将你的机器的时区改为China即可;（具体见上面）。经过测试，在手机端上正常显示。
* 效果图
![Image](https://github.com/happy-running/andriodrabpic/blob/master/s1.jpg)
# 安卓开发，期中第二题

接着实现添加笔记查询功能（根据标题查询），我们知道，我们刚开始开发笔记本这个APP时，我们就可以看到全部的笔记，这些数据从哪里来？可以是存在文件中、内存中，但是阅读源码我们发现，它是从数据库中取出来的，只不过，它在查询数据库时没有设置where后面的条件，即将数据库笔记表中的所有数据都查了出来，查询的核心源码如下（其实最开始源码中selection为null，但是为了可以改变查询条件，将其定义为一个字符串变量，方便查询）
```
cursor = managedQuery(
    getIntent().getData(),// Use the default content URI for the provider.
    PROJECTION,     // Return the note ID and title for each note.
    selection,      // No where clause, return all records.查询条件
    null,           // No where clause, therefore no where column values.
    NotePad.Notes.DEFAULT_SORT_ORDER  // Use the default sort order.
    );
```
其实到了这里就已经很简单了，我们要做的就是通过改变selection的值来设置查询条件，进而实现根据标题的查询功能。
```
selection = "notes.title like '%" + data + "%'";
```
现在的关键又变成了如何获取data的值了，为了实现与用户的交互，我自定义了一个InputDialog类，它继承AlertDialog类，实现了OnClickListener接口，并在顶部菜单栏添加了一个搜索图标，点击这个图标时，可以弹出一个搜索框，用户在上面输入要查询的内容后，点击搜索按钮，此时触发结束编辑事件，如下：
```
public void editInputFinished(String edit) {
    '''
}
```
这个edit的值就是刚刚在搜索框输入的值，之后，获取该值并拼接相关字符串形成selection的值（参考上面的），然后我们再使用和显示所有数据一样的方法既可以根据标题来查询笔记了（当然一般要使用模糊查询）
```
// 查询数据
selection = "notes.title like '%" + data + "%'";
cursor = managedQuery(
        getIntent().getData(),// Use the default content URI for the provider.
        PROJECTION, // Return the note ID and title for each note.
        selection,  // No where clause, return all records.查询条件
        null,       // No where clause, therefore no where column values.
        NotePad.Notes.DEFAULT_SORT_ORDER // Use the default sort order.
);
```
然后将查到的数据适配到控件中即可，如下
```
adapter = new SimpleCursorAdapter(
        NotesList.this, // The Context for the ListView
        R.layout.noteslist_item,// Points to the XML for a list item
        cursor,         // The cursor to get items from
        dataColumns,
        viewIDs
);
show(cursor, adapter);
```
此外，其他的实现（如将时间按日期显示）和第一题一样，这里不再说明，还有就是控件的美化，布局等细节，这里也不具体说明，具体可以查看源码。经过手机端的测试，发现成功运行了根据标题查询的功能，具体的截图如下：
查询功能：
![Image](https://github.com/happy-running/andriodrabpic/blob/master/s21.jpg)
查询结果：
![Image](https://github.com/happy-running/andriodrabpic/blob/master/s22.jpg)
