package br.com.noobs.corpool.ui.trip.create

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.noobs.corpool.BuildConfig
import br.com.noobs.corpool.R
import br.com.noobs.corpool.dto.CreateTripDto
import br.com.noobs.corpool.model.Location
import br.com.noobs.corpool.provider.TripProvider
import br.com.noobs.corpool.ui.components.LoadingDialogBar
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


class CreateTripFragment : Fragment() {

    private val viewModel: CreateTripViewModel by viewModels()


    private val user = FirebaseAuth.getInstance().currentUser
    private val provider = TripProvider()
    private lateinit var dateBtn: Button
    private lateinit var timeBtn: Button
    private lateinit var loadingDialogBar: LoadingDialogBar
    private lateinit var priceEt: EditText
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var defineLocationView: View
    private lateinit var defineDateView: View
    private lateinit var definePriceView: View

    private var selectedDate: LocalDate? = null
    private var selectedTime: LocalTime? = null
    private var selectedLoc: Location? = null

    private var position = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_trip, container, false)



        loadingDialogBar = LoadingDialogBar(this.requireContext())

        priceEt = view.findViewById(R.id.et_price)
        dateBtn = view.findViewById(R.id.btn_select_date)
        timeBtn = view.findViewById(R.id.btn_select_date_time)

        defineLocationView = view.findViewById(R.id.define_location_ll)
        defineDateView = view.findViewById(R.id.define_start_date_ll)
        definePriceView = view.findViewById(R.id.define_price_ll)

        backButton = view.findViewById(R.id.back_button)
        nextButton = view.findViewById(R.id.next_button)
        initializeMap()

        nextButton.setOnClickListener {
            when (position) {
                0 -> {
                    if (selectedLoc == null) {
                        Toast.makeText(
                            this.requireContext(),
                            "Selecione um destino",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        return@setOnClickListener
                    }
                    backButton.text = getText(R.string.back)
                    defineLocationView.visibility = View.GONE
                    defineDateView.visibility = View.VISIBLE
                    position++
                }

                1 -> {
                    if (selectedDate == null) {
                        Toast.makeText(
                            this.requireContext(),
                            "Selecione uma data",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        return@setOnClickListener
                    }
                    nextButton.text = getText(R.string.create)
                    defineDateView.visibility = View.GONE
                    definePriceView.visibility = View.VISIBLE
                    position++
                }

                2 -> {
                    if (priceEt.text.toString().isEmpty()) {
                        priceEt.error = "Digite um preço"
                        return@setOnClickListener
                    }
                    onCreateTripBtnClick()
                }
            }
        }

        backButton.setOnClickListener {
            when (position) {
                0 -> {
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }

                1 -> {
                    backButton.text = getText(R.string.cancel)
                    defineLocationView.visibility = View.VISIBLE
                    defineDateView.visibility = View.GONE
                    position--
                }

                2 -> {
                    nextButton.text = getText(R.string.next)
                    defineDateView.visibility = View.VISIBLE
                    definePriceView.visibility = View.GONE
                    position--
                }
            }
        }



        dateBtn.setOnClickListener {
            openDateDialog()
        }
        timeBtn.setOnClickListener {
            openTimeDialog()
        }

        return view
    }

    private fun initializeMap() {
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
                    Location(place.name, place.latLng?.latitude, place.latLng?.longitude)
                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync { googleMap ->
                    googleMap.clear()
                    googleMap.addMarker(
                        MarkerOptions().position(
                            LatLng(
                                selectedLoc!!.latitude!!,
                                selectedLoc!!.longitude!!
                            )
                        ).title(selectedLoc!!.name)
                    )
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                selectedLoc!!.latitude!!,
                                selectedLoc!!.longitude!!
                            ), 15f
                        )
                    )
                }
            }

            override fun onError(status: Status) {
                Log.i("TAG", "An error occurred: $status")
            }
        })
    }

    private fun onCreateTripBtnClick() {
        crateTrip(
            CreateTripDto(
                userId = user!!.uid,
                locationName = selectedLoc!!.name!!,
                latitude = selectedLoc!!.latitude!!,
                longitude = selectedLoc!!.longitude!!,
                address = selectedLoc!!.name!!,
                date = ZonedDateTime.of(selectedDate, selectedTime, ZoneId.systemDefault()),
                price = priceEt.text.toString().toDouble()
            )
        )
    }

    private fun crateTrip(
        dtoCreation: CreateTripDto,
    ) {

        loadingDialogBar.showDialog("Criando Viagem")

        provider.saveTrip(dtoCreation, {
            loadingDialogBar.dismissDialog()
            Toast.makeText(context, "Viagem criada com sucesso", Toast.LENGTH_SHORT).show().also {
                findNavController().navigate(R.id.action_navigation_create_trip_to_navigation_home)
            }

        }, {
            loadingDialogBar.dismissDialog()
            Toast.makeText(context, "Erro ao criar viagem", Toast.LENGTH_SHORT).show()
        })
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


    override fun onDestroy() {
        super.onDestroy()
        Places.deinitialize()
    }


}

