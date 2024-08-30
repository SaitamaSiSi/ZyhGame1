package com.zyh.ZyhG1.ui.Canvas;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.core.OnHorizontalChangeListener;
import com.zyh.ZyhG1.core.OnInputChangeListener;
import com.zyh.ZyhG1.core.OnIntInputChangeListener;
import com.zyh.ZyhG1.core.OnVerticalChangeListener;
import com.zyh.ZyhG1.model.BaseObject;
import com.zyh.ZyhG1.model.ImgObject;
import com.zyh.ZyhG1.model.TextObject;

import java.util.Objects;

public class CanvasFragment extends Fragment {
    private OnIntInputChangeListener m_x_listener;
    private OnIntInputChangeListener m_y_listener;
    private OnIntInputChangeListener m_w_listener;
    private OnIntInputChangeListener m_h_listener;
    private OnIntInputChangeListener m_font_size_listener;
    String[] m_font_color_opt;
    private OnInputChangeListener m_font_color_listener;
    private OnInputChangeListener m_border_color_listener;
    String[] m_font_back_color_opt;
    private OnInputChangeListener m_font_back_color_listener;
    private OnIntInputChangeListener m_border_listener;
    private OnIntInputChangeListener m_radius_listener;
    private OnIntInputChangeListener m_line_space_listener;
    private OnIntInputChangeListener m_angle_listener;
    private OnInputChangeListener m_up_listener;
    private OnInputChangeListener m_down_listener;
    private OnHorizontalChangeListener m_horizontal_listener;
    private OnVerticalChangeListener m_vertical_listener;
    private OnInputChangeListener m_text_listener;
    private OnInputChangeListener m_del_listener;
    BaseObject m_object = new BaseObject();
    Context m_context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.canvas_fragment, container, false);

        Init(view);

        return view;
    }

    protected void Init(View view) {
        m_context = this.getContext();
        EditText xET = view.findViewById(R.id.canvas_x);
        xET.setText(String.valueOf(m_object._x));
        xET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!Objects.equals(s.toString(), "")) {
                    m_object._x = Integer.parseInt(s.toString());
                    if (m_x_listener != null) {
                        m_x_listener.onIntInputChanged(m_object._uuid, m_object._x);
                    }
                }
            }
        });
        EditText yET = view.findViewById(R.id.canvas_y);
        yET.setText(String.valueOf(m_object._y));
        yET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!Objects.equals(s.toString(), "")) {
                    m_object._y = Integer.parseInt(s.toString());
                    if (m_y_listener != null) {
                        m_y_listener.onIntInputChanged(m_object._uuid, m_object._y);
                    }
                }
            }
        });
        EditText wET = view.findViewById(R.id.canvas_w);
        wET.setText(String.valueOf(m_object._w));
        wET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!Objects.equals(s.toString(), "")) {
                    m_object._w = Integer.parseInt(s.toString());
                    if (m_w_listener != null) {
                        m_w_listener.onIntInputChanged(m_object._uuid, m_object._w);
                    }
                }
            }
        });
        EditText hET = view.findViewById(R.id.canvas_h);
        hET.setText(String.valueOf(m_object._h));
        hET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!Objects.equals(s.toString(), "")) {
                    m_object._h = Integer.parseInt(s.toString());
                    if (m_h_listener != null) {
                        m_h_listener.onIntInputChanged(m_object._uuid, m_object._h);
                    }
                }
            }
        });

        EditText angleET = view.findViewById(R.id.canvas_angle);
        angleET.setText(String.valueOf(m_object._angle));
        angleET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!Objects.equals(s.toString(), "")) {
                    m_object._angle = Integer.parseInt(s.toString());
                    if (m_angle_listener != null) {
                        m_angle_listener.onIntInputChanged(m_object._uuid, m_object._angle);
                    }
                }
            }
        });

        Button upB = view.findViewById(R.id.canvas_obj_up);
        upB.setOnClickListener(v-> {
            if (m_up_listener != null) {
                m_up_listener.onInputChanged(m_object._uuid, "");
            }
        });
        Button downB = view.findViewById(R.id.canvas_obj_down);
        downB.setOnClickListener(v-> {
            if (m_down_listener != null) {
                m_down_listener.onInputChanged(m_object._uuid, "");
            }
        });
        Button delB = view.findViewById(R.id.canvas_obj_del);
        delB.setOnClickListener(v-> {
            if (m_del_listener != null) {
                m_del_listener.onInputChanged(m_object._uuid, "");
            }
        });

        UseBaseObject(view);
    }

    public void SetBaseObject(BaseObject obj) {
        m_object = obj;
    }
    private void UseBaseObject(View view) {
        if (m_object instanceof ImgObject) {
            view.findViewById(R.id.canvas_font_size_label).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_font_size).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_font_color_label).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_font_color).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_border_color_label).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_border_color).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_font_back_color_label).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_font_back_color).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_border_width_label).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_border_width).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_radius_label).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_radius).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_line_space_label).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_line_space).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_horizontal_label).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_horizontal).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_vertical_label).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_vertical).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_text_label).setVisibility(View.GONE);
            view.findViewById(R.id.canvas_text).setVisibility(View.GONE);
        } else if (m_object instanceof TextObject) {
            InitText(view);
        }
    }

    private void InitText(View view) {
        TextObject textObj = (TextObject) m_object;

        EditText fontSizeET = view.findViewById(R.id.canvas_font_size);
        fontSizeET.setText(String.valueOf(textObj._fontSize));
        fontSizeET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!Objects.equals(s.toString(), "")) {
                    textObj._fontSize = Integer.parseInt(s.toString());
                    if (m_font_size_listener != null) {
                        m_font_size_listener.onIntInputChanged(m_object._uuid, textObj._fontSize);
                    }
                }
            }
        });

        EditText hET = view.findViewById(R.id.canvas_h);
        hET.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == KeyEvent.KEYCODE_NAVIGATE_NEXT || actionId == EditorInfo.IME_ACTION_NEXT) {
                fontSizeET.requestFocus();
                return true;
            }
            return false;
        });

        Spinner fontColorS = view.findViewById(R.id.canvas_font_color);
        m_font_color_opt = getResources().getStringArray(R.array.ColorType);
        // 创建一个包含选项的数组适配器
        ArrayAdapter<CharSequence> fontColorAdapter = ArrayAdapter.createFromResource(m_context,
                R.array.ColorType, android.R.layout.simple_spinner_item);
        // 设置下拉选项框的样式
        fontColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将适配器设置到Spinner中
        fontColorS.setAdapter(fontColorAdapter);
        // 遍历Adapter中的数据
        for (int i = 0; i < m_font_color_opt.length; i++) {
            if (Objects.equals(m_font_color_opt[i], textObj._fontColor)) {
                fontColorS.setSelection(i);
            }
        }
        fontColorS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textObj._fontColor = m_font_color_opt[position];
                if (m_font_color_listener != null) {
                    m_font_color_listener.onInputChanged(m_object._uuid, textObj._fontColor);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 在这里处理没有选项被选中的逻辑（如果需要）
            }
        });

        Spinner borderColorS = view.findViewById(R.id.canvas_border_color);
        // 将适配器设置到Spinner中
        borderColorS.setAdapter(fontColorAdapter);
        // 遍历Adapter中的数据
        for (int i = 0; i < m_font_color_opt.length; i++) {
            if (Objects.equals(m_font_color_opt[i], textObj._borderColor)) {
                borderColorS.setSelection(i);
            }
        }
        borderColorS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textObj._borderColor = m_font_color_opt[position];
                if (m_border_color_listener != null) {
                    m_border_color_listener.onInputChanged(m_object._uuid, textObj._borderColor);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 在这里处理没有选项被选中的逻辑（如果需要）
            }
        });

        Spinner fontBackColorS = view.findViewById(R.id.canvas_font_back_color);
        m_font_back_color_opt = getResources().getStringArray(R.array.ColorType2);
        // 创建一个包含选项的数组适配器
        ArrayAdapter<CharSequence> fontBackColorAdapter = ArrayAdapter.createFromResource(m_context,
                R.array.ColorType2, android.R.layout.simple_spinner_item);
        // 设置下拉选项框的样式
        fontBackColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将适配器设置到Spinner中
        fontBackColorS.setAdapter(fontBackColorAdapter);
        // 遍历Adapter中的数据
        for (int i = 0; i < m_font_back_color_opt.length; i++) {
            if (Objects.equals(m_font_back_color_opt[i], textObj._fontBackColor)) {
                fontBackColorS.setSelection(i);
            }
        }
        fontBackColorS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textObj._fontBackColor = m_font_back_color_opt[position];
                if (m_font_back_color_listener != null) {
                    m_font_back_color_listener.onInputChanged(m_object._uuid, textObj._fontBackColor);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 在这里处理没有选项被选中的逻辑（如果需要）
            }
        });

        EditText borderET = view.findViewById(R.id.canvas_border_width);
        borderET.setText(String.valueOf(textObj._borderWidth));
        borderET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!Objects.equals(s.toString(), "")) {
                    textObj._borderWidth = Integer.parseInt(s.toString());
                    if (m_border_listener != null) {
                        m_border_listener.onIntInputChanged(m_object._uuid, textObj._borderWidth);
                    }
                }
            }
        });

        EditText radiusET = view.findViewById(R.id.canvas_radius);
        radiusET.setText(String.valueOf(textObj._radius));
        radiusET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!Objects.equals(s.toString(), "")) {
                    textObj._radius = Integer.parseInt(s.toString());
                    if (m_radius_listener != null) {
                        m_radius_listener.onIntInputChanged(m_object._uuid, textObj._radius);
                    }
                }
            }
        });

        EditText lineSpaceET = view.findViewById(R.id.canvas_line_space);
        lineSpaceET.setText(String.valueOf(textObj._lineSpace));
        lineSpaceET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!Objects.equals(s.toString(), "")) {
                    textObj._lineSpace = Integer.parseInt(s.toString());
                    if (m_line_space_listener != null) {
                        m_line_space_listener.onIntInputChanged(m_object._uuid, textObj._lineSpace);
                    }
                }
            }
        });

        Spinner horizontalS = view.findViewById(R.id.canvas_horizontal);
        // 创建一个包含选项的数组适配器
        ArrayAdapter<CharSequence> horizontalAdapter = ArrayAdapter.createFromResource(m_context,
                R.array.HorizontalType, android.R.layout.simple_spinner_item);
        // 设置下拉选项框的样式
        horizontalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将适配器设置到Spinner中
        horizontalS.setAdapter(horizontalAdapter);
        if (textObj._horizontal == TextObject.HorizontalType.CENTER) {
            horizontalS.setSelection(1);
        } else if (textObj._horizontal == TextObject.HorizontalType.RIGHT) {
            horizontalS.setSelection(2);
        } else {
            horizontalS.setSelection(0);
        }
        horizontalS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                    default:
                        textObj._horizontal = TextObject.HorizontalType.LEFT;
                        break;
                    case 1:
                        textObj._horizontal = TextObject.HorizontalType.CENTER;
                        break;
                    case 2:
                        textObj._horizontal = TextObject.HorizontalType.RIGHT;
                        break;
                }
                if (m_horizontal_listener != null) {
                    m_horizontal_listener.onHorizontalChanged(m_object._uuid, textObj._horizontal);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 在这里处理没有选项被选中的逻辑（如果需要）
            }
        });

        Spinner verticalS = view.findViewById(R.id.canvas_vertical);
        // 创建一个包含选项的数组适配器
        ArrayAdapter<CharSequence> verticalAdapter = ArrayAdapter.createFromResource(m_context,
                R.array.VerticalType, android.R.layout.simple_spinner_item);
        // 设置下拉选项框的样式
        verticalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将适配器设置到Spinner中
        verticalS.setAdapter(verticalAdapter);
        if (textObj._vertical == TextObject.VerticalType.MIDDLE) {
            verticalS.setSelection(1);
        } else if (textObj._vertical == TextObject.VerticalType.BOTTOM) {
            verticalS.setSelection(2);
        } else {
            verticalS.setSelection(0);
        }
        verticalS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                    default:
                        textObj._vertical = TextObject.VerticalType.TOP;
                        break;
                    case 1:
                        textObj._vertical = TextObject.VerticalType.MIDDLE;
                        break;
                    case 2:
                        textObj._vertical = TextObject.VerticalType.BOTTOM;
                        break;
                }
                if (m_vertical_listener != null) {
                    m_vertical_listener.onVerticalChanged(m_object._uuid, textObj._vertical);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 在这里处理没有选项被选中的逻辑（如果需要）
            }
        });

        EditText textET = view.findViewById(R.id.canvas_text);
        textET.setText(textObj._text);
        textET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                textObj._text = s.toString();
                if (m_text_listener != null) {
                    m_text_listener.onInputChanged(m_object._uuid, textObj._text);
                }
            }
        });
    }

    public void SetXChangeListener(OnIntInputChangeListener listener) {
        this.m_x_listener = listener;
    }
    public void SetYChangeListener(OnIntInputChangeListener listener) {
        this.m_y_listener = listener;
    }
    public void SetWChangeListener(OnIntInputChangeListener listener) {
        this.m_w_listener = listener;
    }
    public void SetHChangeListener(OnIntInputChangeListener listener) {
        this.m_h_listener = listener;
    }
    public void SetFontSizeChangeListener(OnIntInputChangeListener listener) {
        this.m_font_size_listener = listener;
    }
    public void SetFontColorChangeListener(OnInputChangeListener listener) {
        this.m_font_color_listener = listener;
    }
    public void SetBorderColorChangeListener(OnInputChangeListener listener) {
        this.m_border_color_listener = listener;
    }
    public void SetFontBackColorChangeListener(OnInputChangeListener listener) {
        this.m_font_back_color_listener = listener;
    }
    public void SetBorderWidthChangeListener(OnIntInputChangeListener listener) {
        this.m_border_listener = listener;
    }
    public void SetRadiusChangeListener(OnIntInputChangeListener listener) {
        this.m_radius_listener = listener;
    }
    public void SetLineSpaceChangeListener(OnIntInputChangeListener listener) {
        this.m_line_space_listener = listener;
    }
    public void SetAngleChangeListener(OnIntInputChangeListener listener) {
        this.m_angle_listener = listener;
    }
    public void SetUpListener(OnInputChangeListener listener) {
        this.m_up_listener = listener;
    }
    public void SetDownListener(OnInputChangeListener listener) {
        this.m_down_listener = listener;
    }
    public void SetHorizontalChangeListener(OnHorizontalChangeListener listener) {
        this.m_horizontal_listener = listener;
    }
    public void SetVerticalChangeListener(OnVerticalChangeListener listener) {
        this.m_vertical_listener = listener;
    }
    public void SetTextChangeListener(OnInputChangeListener listener) {
        this.m_text_listener = listener;
    }
    public void SetDelListener(OnInputChangeListener listener) {
        this.m_del_listener = listener;
    }
}
