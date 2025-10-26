# Team Contributions — PersonalRestaurantGuide Prototype

## Overview
This project was built collaboratively by a 4-member team.  
Each member owned a distinct vertical slice — from the data layer to UI logic — ensuring end-to-end functionality and at least 80% feature completion.

---

### 🧑‍💻 Dhairya Gohel — Data Layer & App Boot
**Responsibilities**
- Built complete Room Database stack: Entity, DAO, Repository, ViewModel.
- Implemented `SplashActivity` with seed data logic.
- Created database methods for add, delete, search, and setRating.
- Defined Material3 theme and app structure.

**Key Files**
- `data/RestaurantEntity.java`
- `data/RestaurantDao.java`
- `data/AppDatabase.java`
- `data/RestaurantRepository.java`
- `data/RestaurantViewModel.java`
- `SplashActivity.java`

**Verification**
- App launches with pre-seeded demo data.
- Database persists new entries, deletes, and ratings across sessions.

---

### 👨‍🎨 Het Jasani — Restaurant List Screen
**Responsibilities**
- Built RecyclerView Adapter + ViewHolder with stars and tags display.
- Implemented LiveData-driven search and swipe-to-delete.
- Fixed FAB placement using ConstraintLayout (bottom-end anchored).

**Key Files**
- `ui/list/RestaurantListFragment.java`
- `ui/list/RestaurantAdapter.java`
- `layout/fragment_restaurant_list.xml`
- `layout/item_restaurant.xml`

**Verification**
- Live search filters by name/tags instantly.
- Swipe to delete works with snackbar feedback.
- List updates dynamically when items are added or rated.

---

### 👩‍💼 Parv Mehta — Add/Edit Restaurant Flow
**Responsibilities**
- Developed the Add/Edit screen with Material TextInputLayouts.
- Handled validation for required fields.
- Integrated form save via ViewModel and navigation back to list.

**Key Files**
- `ui/edit/AddEditRestaurantFragment.java`
- `layout/fragment_add_edit_restaurant.xml`

**Verification**
- Clicking FAB opens Add screen.
- Save adds new restaurant to DB and shows immediately in list.

---

### 👨‍🔧 Tirth Rabadiya — Details Screen & Rating Feature
**Responsibilities**
- Redesigned details page layout (MaterialCardView sections, buttons).
- Implemented fully functional interactive RatingBar.
- Added Share, Map, Directions, and Dial actions.

**Key Files**
- `ui/detail/RestaurantDetailFragment.java`
- `layout/fragment_restaurant_detail.xml`

**Verification**
- Clicking a restaurant shows correct details.
- Ratings can be changed interactively and persisted.
- Share/Map/Directions/Phone actions verified on emulator.

---

## 🧩 Integration
- Navigation Graph: List → Details / AddEdit.
- Room + LiveData ensure reactive updates.
- Material3 applied globally via `Theme.PersonalRestaurantGuide`.

---

## ✅ Summary
| Member | Area | Key Contribution |
|---------|-------|------------------|
| Dhairya Gohel | Data & Architecture | Room DB, Splash seed, Repository |
| Het Jasani | UX / List | RecyclerView, Search, Delete, FAB |
| Parv Mehta | Add/Edit Flow | Input validation, Save logic |
| Tirth Rabadiya | Details / Rating | Actions, Rating persistence, Redesign |

---

### Version
**Prototype v0.2 — Improved UI, Material3 consistency, persisted ratings.**
