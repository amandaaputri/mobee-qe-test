# TEST CASE
https://docs.google.com/spreadsheets/d/1MdqBP2hEwXTv5eScPnUyhcT0WjHsk7w49oAL73In8uw/edit?usp=sharing


# QA Notes

Selama testing di fitur Instant Convert, ada beberapa hal yang bisa ditingkatkan baik dari sisi API/backend maupun UI/UX:

1.	Error message kurang tepat
Saat kirim amount_from = -100, API balikin error:
	both from and to amount are not provided
Padahal di request body, field from dan to jelas sudah ada. Sehingga error jadi misleading, karena sebenarnya masalahnya ada di nilai amount (negatif), bukan field yang hilang.

2.	Validasi belum granular
Saat ini, semua error seperti digeneralisir jadi “field tidak ada”. Akan lebih jelas kalau backend bedain:
- Field kosong
- Tipe data salah (misalnya string bukannya number)
- Nilai tidak valid (negatif, nol, atau di bawah minimum)

3.	Indikator & Feedback untuk Field Wajib
Saat ini, kalau field from atau to kosong, tombol Convert otomatis tidak bisa ditekan. Namun, user mungkin saja tidak tahu penyebabnya karena tidak ada tanda bahwa field tersebut wajib diisi.
Akibatnya, dari sisi UX terkesan membingungkan (button mati tanpa alasan jelas).

Saran perbaikan:
- Tambahkan tanda * merah pada label field wajib (from, to, amount) agar user langsung tahu input tersebut mandatory.
- Jika field kosong, berikan inline error message atau keterangan jelas, jadi bukan hanya membuat button tidak aktif.
Contoh: "Field From wajib diisi" atau "Pilih aset tujuan terlebih dahulu".
