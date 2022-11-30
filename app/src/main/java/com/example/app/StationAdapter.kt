package com.example.app

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

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
            listener?.onDetails(station.id, 1)
        }

        setImvPicture(holder, station)

        setFavorite(holder, station)
    }


    override fun getItemCount(): Int {
        return stations.size
    }

    /**
     * Set the text for the name of the station.
     * @param station : station to get the name from
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
     * @param station : station to get the price from
     */
    private fun setTxvPrice(station: Station): String {
        return if (station.price_val.filterNotNull().isNotEmpty()) {
            station.price_val.filterNotNull().min().toString() + "€"
        } else {
            ""
        }
    }

    /**
     * Set the image for the favorite button.
     * @param holder : holder of the view
     * @param station : station to set favorite
     */
    private fun setFavorite(holder: StationViewHolder, station: Station) {
        if (station.fav) {
            holder.imvFav.setImageResource(R.drawable.fav_on_24)
        } else {
            holder.imvFav.setImageResource(R.drawable.fav_off_24)
        }
    }

    /**
     * Set the image for the station.
     * @param holder : holder of the view
     * @param station : station to set image
     */
    private fun setImvPicture(holder: StationViewHolder, station: Station) {
        val img: ImageView = holder.imvPicture
        when (station.brand) {
            "Shopi" -> Picasso.get()
                .load("https://www.peimg.fr/annonceurs/images/65404dpeI5Y9Iua_fb.gif")
                .into(img)
            "Leclerc" -> Picasso.get()
                .load("https://blackfriday-en-france.com/wp-content/uploads/2021/11/258983318_1034929400635838_3374731671234093738_n-300x300.jpg")
                .into(img)
            "Super Casino" -> Picasso.get()
                .load("https://stationessence.com/m/p/t/super-casino-1p.png")
                .into(img)
            "Total" -> Picasso.get()
                .load("https://cdn.motor1.com/images/mgl/pVMJ0/s1/logo-totalenergies.jpg")
                .into(img)
            "TOTAL" -> Picasso.get()
                .load("https://cdn.motor1.com/images/mgl/pVMJ0/s1/logo-totalenergies.jpg")
                .into(img)
            "Simply Market" -> Picasso.get()
                .load("https://www.toute-la-franchise.com/images/zoom/reseaux/tgrd_centre/9052.jpg")
                .into(img)
            "Esso Express" -> Picasso.get()
                .load("https://assets.justacote.com/photos_entreprises/esso-express-paris-14102681120.jpg")
                .into(img)
            "BP" -> Picasso.get()
                .load("https://www.misteroil.fr/assets/images/pictures/shop_brand/c_c/300x200/logo-bp_1609245223.png")
                .into(img)
            "BP Express" -> Picasso.get()
                .load("https://www.misteroil.fr/assets/images/pictures/shop_brand/c_c/300x200/logo-bp_1609245223.png")
                .into(img)
            "Esso" -> Picasso.get()
                .load("https://assets.justacote.com/photos_entreprises/esso-express-paris-14102681120.jpg")
                .into(img)
            "Carrefour" -> Picasso.get()
                .load("https://www.sodexo.fr/fstrz/r/s/aziest1hxq182.blob.core.windows.net/fr-uploads/2018/08/logo-carrefour-2.png")
                .into(img)
            "Intermarché" -> Picasso.get()
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNeu8aoshH57-pup3olzQZ1TFDIenWEoHwOw&usqp=CAU")
                .into(img)
            "Intermarché Contact" -> Picasso.get()
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNeu8aoshH57-pup3olzQZ1TFDIenWEoHwOw&usqp=CAU")
                .into(img)
            "Ecomarché" -> Picasso.get()
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNeu8aoshH57-pup3olzQZ1TFDIenWEoHwOw&usqp=CAU")
                .into(img)
            "Carrefour market" -> Picasso.get()
                .load("data:image/png;base64,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")
                .into(img)
            "Avia" -> Picasso.get()
                .load("https://upload.wikimedia.org/wikipedia/commons/thumb/c/c0/AVIA_International_logo.svg/2560px-AVIA_International_logo.svg.png")
                .into(img)
            "Elan" -> Picasso.get()
                .load("https://www.carburants.org/i/stations/elan.png")
                .into(img)
            "Cora" -> Picasso.get()
                .load("https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Cora_logo.svg/2456px-Cora_logo.svg.png")
                .into(img)
            "Auchan" -> Picasso.get()
                .load("https://dirigeants-entreprise.com/content/uploads/auchan.png")
                .into(img)
            "Système U" -> Picasso.get()
                .load("https://media-exp1.licdn.com/dms/image/C4D0BAQHMyljF0W9vhA/company-logo_200_200/0/1599659893967?e=2147483647&v=beta&t=2Dx-GmPOEOyqsIu7ZFr9uB3zkbaSHzsfhLkxq-96oZs")
                .into(img)
            "Monoprix" -> Picasso.get()
                .load("https://play-lh.googleusercontent.com/n_me-GfEzFmxm6dKXp8AhLS5w6hgpOB2NMiq-3Pav1uD9ygPT3E3weWm9Yh9bvguxdqP")
                .into(img)
            "Total Access" -> Picasso.get()
                .load("https://www.stations-carburant.com/uploads/documents/totalaccess.png")
                .into(img)
            "Casino" -> Picasso.get()
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRhUiaO01GtaTssgwFOXWp1LXmDL543_dA5NpWDDMZRMNnzxxqkCDrgoGHGQW2dKCYl9WA&usqp=CAU")
                .into(img)
            "Super Casino" -> Picasso.get()
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRhUiaO01GtaTssgwFOXWp1LXmDL543_dA5NpWDDMZRMNnzxxqkCDrgoGHGQW2dKCYl9WA&usqp=CAU")
                .into(img)
            "Shell" -> Picasso.get()
                .load("https://www.1min30.com/wp-content/uploads/2017/06/Shell-logo.jpg")
                .into(img)
            "Atac" -> Picasso.get()
                .load("https://www.pagesjaunes.fr/media/vignette/AAA1PDUOOKQZ-C73007.gif")
                .into(img)
            "Netto" -> Picasso.get()
                .load("data:image/png;base64,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")
                .into(img)
            "Agip" -> Picasso.get()
                .load("https://www.vanguardngr.com/wp-content/uploads/2020/02/1569223644-34-nigerian-agip-oil-company-limited-nigeria-naoc.jpg")
                .into(img)
            "Géant" -> Picasso.get()
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRhUiaO01GtaTssgwFOXWp1LXmDL543_dA5NpWDDMZRMNnzxxqkCDrgoGHGQW2dKCYl9WA&usqp=CAU")
                .into(img)
            else -> Picasso.get()
                .load("https://st4.depositphotos.com/12229170/27918/v/450/depositphotos_279180872-stock-illustration-gas-station-glyph-icon-fuel.jpg")
                .into(img)
        }
    }
}