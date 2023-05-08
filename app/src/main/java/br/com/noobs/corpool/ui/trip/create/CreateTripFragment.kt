package br.com.noobs.corpool.ui.trip.create

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import br.com.noobs.corpool.BuildConfig
import br.com.noobs.corpool.R
import br.com.noobs.corpool.database.DatabaseManager
import br.com.noobs.corpool.model.Location
import br.com.noobs.corpool.model.TripItem
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


class CreateTripFragment : Fragment() {

    companion object {
        fun newInstance() = CreateTripFragment()
    }

    private lateinit var viewModel: CreateTripViewModel

    private lateinit var dateBtn: Button
    private lateinit var timeBtn: Button
    private var selectedDate: LocalDate? = null
    private var selectedTime: LocalTime? = null
    private var selectedLoc: Location? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_trip, container, false)

        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), BuildConfig.API_KEY, Locale.getDefault())
        }


        val autoCompleteFragment =
            childFragmentManager.findFragmentById(R.id.et_destiny) as AutocompleteSupportFragment

        autoCompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )
        autoCompleteFragment.setHint("buscar...")

        autoCompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                selectedLoc =
                    Location(place.name!!, place.latLng!!.latitude, place.latLng!!.longitude)
                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync { googleMap ->
                    googleMap.clear()
                    googleMap.addMarker(
                        MarkerOptions().position(
                            LatLng(
                                selectedLoc!!.latitude,
                                selectedLoc!!.longitude
                            )
                        ).title(selectedLoc!!.name)
                    )
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                selectedLoc!!.latitude,
                                selectedLoc!!.longitude
                            ), 15f
                        )
                    )
                }
            }

            override fun onError(status: Status) {
                Log.i("TAG", "An error occurred: $status")
            }
        })

        dateBtn = view.findViewById(R.id.btn_select_date)
        timeBtn = view.findViewById(R.id.btn_select_date_time)

        dateBtn.setOnClickListener {
            openDateDialog()
        }
        timeBtn.setOnClickListener {
            openTimeDialog()
        }

        val createTripBtn = view.findViewById<Button>(R.id.btn_create_trip)

        createTripBtn.setOnClickListener {
            onCreateTripBtnClick()

        }
        return view
    }

    private fun onCreateTripBtnClick() {
        if (!validateData()) {
            return
        }
        insertOnDatabase()
        moveToViewHomeViewFragment()
    }

    private fun moveToViewHomeViewFragment() {
        Navigation.findNavController(requireView()).navigate(R.id.navigation_home)

    }

    private fun insertOnDatabase() {
        val priceEt = view?.findViewById<EditText>(R.id.et_price)
        val price = priceEt!!.text.toString().toDouble()
        val trip = TripItem(
            address = selectedLoc!!.name,
            date = ZonedDateTime.of(selectedDate, selectedTime, TimeZone.getDefault().toZoneId()),
            price = price,
            location = selectedLoc!!
        )
        val db = DatabaseManager(this.requireContext(), "trips")
        db.insertTrip(trip)
    }

    private fun validateData(): Boolean {
        if (selectedLoc == null) {
            Toast.makeText(this.requireContext(), "Selecione um destino", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        val priceEt = view?.findViewById<EditText>(R.id.et_price)
        if (priceEt!!.text.toString().isEmpty()) {
            priceEt.error = "Digite um preço"
            return false
        }
        if (selectedDate == null) {
            dateBtn.error = "Selecione uma data"
            return false

        }
        if (selectedTime == null) {
            timeBtn.error = "Selecione uma hora"
            return false
        }
        return true
    }


    private fun openDateDialog() {
        val now = Calendar.getInstance()
        val datePicker = DatePickerDialog(this.requireContext(), { _, year, month, dayOfMonth ->

            selectedDate = LocalDate.of(year, month, dayOfMonth)
            dateBtn.text = selectedDate!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))

        datePicker.show()


    }

    private fun openTimeDialog() {
        val now = Calendar.getInstance()
        val datePicker = TimePickerDialog(this.requireContext(), { _, hour, minute ->

            selectedTime = LocalTime.of(hour, minute)
            timeBtn.text = selectedTime!!.format(DateTimeFormatter.ofPattern("HH:mm"))


        }, now.get(Calendar.HOUR), now.get(Calendar.MINUTE), true)

        datePicker.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateTripViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        Places.deinitialize()
    }


}

