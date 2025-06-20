-- USERS
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    phone TEXT UNIQUE,
    email TEXT UNIQUE,
    loyalty_points INTEGER DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- SERVICES
CREATE TABLE services (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    description TEXT,
    duration INTEGER NOT NULL CHECK (duration > 0),
    price NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
    category_id UUID,
    image_url TEXT
);

-- MASTERS
CREATE TABLE masters (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    specialization TEXT,
    rating NUMERIC(2, 1) CHECK (rating >= 0 AND rating <= 5),
    photo_url TEXT,
    working_hours JSONB -- пример: {"mon": "10:00-18:00", "tue": "off", ...}
);

-- APPOINTMENTS
CREATE TABLE appointments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    master_id UUID NOT NULL REFERENCES masters(id) ON DELETE SET NULL,
    service_id UUID NOT NULL REFERENCES services(id) ON DELETE SET NULL,
    date_time TIMESTAMP WITH TIME ZONE NOT NULL,
    status TEXT NOT NULL CHECK (status IN ('подтверждена', 'отменена', 'завершена')),
    notes TEXT,
    total_price NUMERIC(10, 2) NOT NULL CHECK (total_price >= 0)
);

-- REVIEWS
CREATE TABLE reviews (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    appointment_id UUID NOT NULL REFERENCES appointments(id) ON DELETE CASCADE,
    rating INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    photos JSONB, -- пример: ["url1", "url2"]
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- CATEGORIES
CREATE TABLE categories (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL
);

ALTER TABLE services
ADD CONSTRAINT fk_category
FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL;
