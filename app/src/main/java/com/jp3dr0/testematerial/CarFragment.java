package com.jp3dr0.testematerial;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.jp3dr0.testematerial.adapters.CarAdapter;

import java.util.List;

import butterknife.BindView;

public class CarFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    // Variaveis de intancia do recycler view e da lista de carros
    private RecyclerView mRecyclerView;
    private List<Car> mList;
    private CarAdapter adapter;
    String stringText;

    public void Teste(String texto){
        Toast.makeText(getActivity(), texto, Toast.LENGTH_SHORT).show();
    }

    // Equivalente ao onCreate() da Activity
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car, container, false);

        stringText = getArguments().getString("data");
        Toast.makeText(getActivity(), "eu, o fragment, recebi esse valor da activity: " + stringText, Toast.LENGTH_SHORT).show();

        //mRecyclerView.setLayoutManager(isSwitched ? new GridLayoutManager(this, 2) : new LinearLayoutManager(this));

        // Bind do RV
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);

        // Puxando 10 itens pra lista (usando o metodo da MainActivity) - Poderia ser da INTERNET
        mList = ((MainActivity) getActivity()).getSetCarList(10);

        // Chamando o metodo pra configurar o RV
        configurarRecyclerView();

        return view;
    }

    // Código de configuração do RecyclerView
    private void configurarRecyclerView() {
        Toast.makeText(getActivity(), "configurando RV no fragment", Toast.LENGTH_SHORT).show();

        // Isso melhora o desempenho do RV
        mRecyclerView.setHasFixedSize(true);

        // Aqui vai a lógica de quando o usuário baixar o scroll, para carregar mais itens
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(stringText.equals("false")){
                    // LINEAR LAYOUT
                    LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();

                    // GRID LAYOT
                    //GridLayoutManager llm = (GridLayoutManager) mRecyclerView.getLayoutManager();
                    //CarAdapter adapter = (CarAdapter) mRecyclerView.getAdapter();

                    // Se o tamanho da lista for igual a posição do ultimo item, quer dizer que esta mostrando o ultimo item
                    // Entao queremos carregar mais 10 itens

                    if (mList.size() == llm.findLastCompletelyVisibleItemPosition() + 1) {

                        // Vamos puxar mais 10 itens e carregar um por um (poderia ser puxar da internet)

                        List<Car> listAux = ((MainActivity) getActivity()).getSetCarList(10);

                        for (int i = 0; i < listAux.size(); i++) {
                            adapter.addListItem(listAux.get(i), mList.size());
                        }
                    }
                }
                else{
                    // STAGGERED GRID LAYOUT
                    StaggeredGridLayoutManager llm = (StaggeredGridLayoutManager) mRecyclerView.getLayoutManager();
                    int[] aux = llm.findLastCompletelyVisibleItemPositions(null);
                    int max = -1;
                    for(int i = 0; i < aux.length; i++){
                        max = aux[i] > max ? aux[i] : max;
                    }

                    adapter = (CarAdapter) mRecyclerView.getAdapter();

                    if (mList.size() == max + 1) {

                        // Vamos puxar mais 10 itens e carregar um por um (poderia ser puxar da internet)

                        List<Car> listAux = ((MainActivity) getActivity()).getSetCarList(10);

                        for (int i = 0; i < listAux.size(); i++) {
                            adapter.addListItem(listAux.get(i), mList.size());
                        }
                    }
                }

            }
        });

        // Isso vai instanciar a classe RecyclerViewTouchListener que serve exatamente pra isso, "ativar" o Touch Listener do Recycler View
        // O onClickListener() e onLongPressListener(), implementações da interface RecyclerViewOnClickListenerHack, só vão funcionar com isso
        mRecyclerView.addOnItemTouchListener(new ReyclerViewTouchListener(getActivity(), mRecyclerView, this));

        // Configuração do LayoutManager - Linear, Grid ou StaggeredGrid (Grid sem gaps)
        // OBS - o setReverseLayout() ou o ultimo parametro do construtor do grid serve pra deixar a ordem reversa, tipo em chat

        if(stringText.equals("false")){
            // LINEAR LAYOUT

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            //llm.setReverseLayout(true);
            mRecyclerView.setLayoutManager(llm);

            // GRID LAYOUT

            //GridLayoutManager llm = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
            //mRecyclerView.setLayoutManager(llm);
        }
        else{
            // STAGGERED GRID LAYOUT
            StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            llm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            mRecyclerView.setLayoutManager(llm);
        }

        // Configuração do Adapter
        CarAdapter adapter = new CarAdapter((MainActivity) getActivity(), getActivity(), mList);
        //adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter( adapter );

        // Decoração (Opcional)
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    // Aqui vc coloca a lógica de quando o cara clicar em um item
    @Override
    public void onClickListener(View view, int position) {
        Toast.makeText(getActivity(), "onClickListener(): "+position, Toast.LENGTH_SHORT).show();

        //CarAdapter adapter = (CarAdapter) mRecyclerView.getAdapter();
        //adapter.removeListItem(position);
    }

    // Aqui vc coloca a lógica de quando o cara clicar e segurar em um item
    @Override
    public void onLongPressClickListener(View view, int position) {
        Toast.makeText(getActivity(), "onLongPressClickListener(): "+position, Toast.LENGTH_SHORT).show();

        view.setBackgroundColor(view.getContext().getResources().getColor(R.color.colorPrimaryDark));

        //viewHolder.cardView.setCardBackgroundColor(sparseArray.valueAt(i).isSelected() ? Color.LTGRAY : Color.WHITE);

        MainActivity activity = ((MainActivity) getActivity());

        activity.setLong_press_item_on_fragment(true);

        //activity.myBackground.setSelected(true);

        if( !activity.getItens_selecionados_no_momento().contains(mList.get(position)) ){
            activity.getItens_selecionados_no_momento().add(mList.get(position));
        }

        activity.tablayout.setVisibility(View.GONE);

        activity.toolbar.setVisibility(Toolbar.GONE);
        activity.toolbar2.setVisibility(Toolbar.VISIBLE);
        activity.setSupportActionBar(activity.toolbar2);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().invalidateOptionsMenu();
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.selected_item_icon_color), PorterDuff.Mode.SRC_ATOP);
        activity.getSupportActionBar().setHomeAsUpIndicator(upArrow);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        activity.getSupportActionBar().setTitle(Integer.toString(activity.getItens_selecionados_no_momento().size()));
        //Toast.makeText(getActivity(), "contador: "+contador_long_clicks, Toast.LENGTH_SHORT).show();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window window = activity.getWindow();

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }

            // finally change the color
            window.setStatusBarColor(activity.getResources().getColor(R.color.status_bar_color_selected));
        }

        activity.setLong_press_item_on_fragment(true);

        //CarAdapter adapter = (CarAdapter) mRecyclerView.getAdapter();
        //adapter.removeListItem(position);
    }

    // Essa é a classe que vai fazer funcionar o Touch Listener no Recycler View. Não precisa mexer aqui
    private static class ReyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
        private Context mContext;
        private GestureDetector mGestureDetector;
        private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

        public ReyclerViewTouchListener(Context mContext, final RecyclerView rv, final RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
            this.mContext = mContext;
            this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;

            mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View cv = rv.findChildViewUnder(e.getX(), e.getY());

                    if(cv != null && mRecyclerViewOnClickListenerHack != null){
                        mRecyclerViewOnClickListenerHack.onLongPressClickListener(cv,
                                rv.getChildPosition(cv) );
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View cv = rv.findChildViewUnder(e.getX(), e.getY());

                    if(cv != null && mRecyclerViewOnClickListenerHack != null){
                        mRecyclerViewOnClickListenerHack.onClickListener(cv,
                                rv.getChildPosition(cv) );
                    }

                    return(true);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
