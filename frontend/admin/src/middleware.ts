import { NextResponse, NextRequest } from 'next/server'

// This function can be marked `async` if using `await` inside
export function middleware(request: NextRequest) {

  const token = request.cookies.get('access_token')?.value;
  const isLogin = !!token;

  if (isLogin) {
    return NextResponse.next();
  } else {
    return NextResponse.redirect(new URL('/login', request.url));
  }

}

export const config = {
  matcher: [
    '/',
    '/category/:path*',
    '/products/:path*'
  ],
}
