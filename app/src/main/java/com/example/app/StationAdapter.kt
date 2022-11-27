package com.example.app

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

/**
 * Adapter for the list of stations.
 */
class StationAdapter(private var stations: List<Station>, private var listener: ListCallBack?) : RecyclerView.Adapter<StationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(
            R.layout.row_station, parent,
            false
        )
        return StationViewHolder(row)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        //Deal with exceptions if the list is empty or null
        val station = stations[position];

        holder.txvCityPC.text = station.address + ", " + station.pc
        holder.txvName.text = setTxvName(station)
        holder.txvPrice.text = setTxvPrice(station)

        holder.imvFav.setOnClickListener {
            station.fav = !station.fav
            setFavorite(holder, station)
            listener?.onFavorite(station.id, station.fav)
        }

        holder.imvDetails.setOnClickListener {
            listener?.onDetails(station.id)
        }

        setImvPicture(holder, station)

        setFavorite(holder, station)
    }


    override fun getItemCount(): Int {
        return stations.size
    }

    /**
     * Set the text for the name of the station.
     */
    private fun setTxvName(station: Station): String {
        return if (station.price_name.filterNotNull().isNotEmpty()) {
            station.price_name.toSet().joinToString(", ")
        } else {
            "Non renseigné"
        }
    }

    /**
     * Set the text for the price of the station.
     */
    private fun setTxvPrice(station: Station): String {
        return if (station.price_val.filterNotNull().isNotEmpty()) {
            station.price_val.filterNotNull().min().toString() + "€"
        } else {
            ""
        }
    }

    private fun setFavorite(holder: StationViewHolder, station: Station) {
        if (station.fav) {
            holder.imvFav.setImageResource(R.drawable.fav_on)
        } else {
            holder.imvFav.setImageResource(R.drawable.fav_off)
        }
    }

    private fun setImvPicture(holder: StationViewHolder, station: Station) {
        val img: ImageView = holder.imvPicture
        when (station.pc) {
            75001 -> Picasso.get()
                .load("https://edito.seloger.com/sites/default/files/styles/735x412/public/edito_migrate/article/image/fotolia_2030074_s.webp?itok=zbYLC-Z7")
                .into(holder.imvPicture)
            75002 -> Picasso.get()
                .load("https://www.book-a-flat.com/photo/arrondissement/6/01-%20place-des-victoires-paris%20(new).jpg")
                .into(holder.imvPicture)
            75003 -> Picasso.get()
                .load("https://www.pariszigzag.fr/wp-content/uploads/2018/02/carreau-du-temple-artistique-paris-zigzag.jpg")
                .into(holder.imvPicture)
            75004 -> Picasso.get()
                .load("https://www.sunlocation.com//images/zones/112/paris-4-place-bastille.jpg")
                .into(img)
            75005 -> Picasso.get()
                .load("https://edito.seloger.com/sites/default/files/styles/480x/public/edito_migrate/article/image/fotolia_51149212_s.jpg?itok=vB1KCAzs")
                .into(img)
            75006 -> Picasso.get()
                .load("https://upload.wikimedia.org/wikipedia/commons/thumb/6/64/P1090420_Paris_VI_place_Saint-Sulpice_rwk.JPG/1200px-P1090420_Paris_VI_place_Saint-Sulpice_rwk.JPG")
                .into(img)
            75007 -> Picasso.get()
                .load("https://escaledenuit.com/wp-content/uploads/2021/01/La-fameuse-perspective-du-Pont-Alexandre-III-et-des-Invalides-e1623328263783.jpg")
                .into(holder.imvPicture)
            75008 -> Picasso.get()
                .load("https://www.sunlocation.com//images/zones/112/paris-8-champs-elysees.jpg")
                .into(img)
            75009 -> Picasso.get()
                .load("https://upload.wikimedia.org/wikipedia/commons/thumb/5/57/Facade_of_Op%C3%A9ra_Garnier%2C_France_2011.jpg/1200px-Facade_of_Op%C3%A9ra_Garnier%2C_France_2011.jpg")
                .into(img)
            75010 -> Picasso.get()
                .load("https://vivreparis.fr/wp-content/uploads/2019/08/canal-saint-martin.jpg")
                .into(img)
            75011 -> Picasso.get()
                .load("https://www.myexpat.fr/wp-content/uploads/fotolia_63303099_s-600x400.jpg")
                .into(img)
            75012 -> Picasso.get()
                .load("https://vivreparis.fr/wp-content/uploads/2018/05/shutterstock_730699129.jpg")
                .into(img)
            75013 -> Picasso.get()
                .load("https://www.pariszigzag.fr/wp-content/uploads/2020/06/villa-daviel-paris-zigzag.png")
                .into(img)
            75014 -> Picasso.get()
                .load("https://www.parisinfo.com/var/otcp/sites/images/node_43/node_51/node_232/rue-daguerre-%7C-630x405-%7C-%C2%A9-studio-ttg/19791231-1-fre-FR/Rue-Daguerre-%7C-630x405-%7C-%C2%A9-Studio-TTG.jpg")
                .into(img)
            75015 -> Picasso.get()
                .load("https://a.travel-assets.com/findyours-php/viewfinder/images/res70/132000/132160-15th-Arrondissement.jpg")
                .into(img)
            75016 -> Picasso.get()
                .load("https://www.pariszigzag.fr/wp-content/uploads/2019/06/paris-16eme-paris-zigzag-e1560959466415.jpg")
                .into(img)
            75017 -> Picasso.get()
                .load("https://www.leparisdelimmobilier.com/wp-content/uploads/2017/07/histoire-17eme-770x448.png")
                .into(img)
            75018 -> Picasso.get()
                .load("https://res.cloudinary.com/lastminute-contenthub/s--ayvqcluJ--/c_limit,h_999999,w_1024/f_auto/q_auto:eco/v1/DAM/Photos/Destinations/Europe/France/Paris/shutterstock_1285435696")
                .into(img)
            75019 -> Picasso.get()
                .load("https://www.myexpat.fr/wp-content/uploads/fotolia_146116357_s-600x400.jpg")
                .into(img)
            75020 -> Picasso.get()
                .load("https://www.myexpat.fr/wp-content/uploads/fotolia_99971213_s-600x400.jpg")
                .into(img)
        }
    }
}