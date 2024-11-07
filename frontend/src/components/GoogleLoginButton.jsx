function GoogleLoginButton() {
  const handleClick = () => {
    const callbackUrl = `${window.location.origin}`;
    const googleClientId =
      "174155438396-4005lrk0pcaq1vif0vdmdnhto8j2nuds.apps.googleusercontent.com";
    const targetUrl = `https://accounts.google.com/o/oauth2/auth?redirect_uri=${encodeURIComponent(
      callbackUrl
    )}&response_type=token&client_id=${googleClientId}&scope=https://www.googleapis.com/auth/calendar https://www.googleapis.com/auth/calendar.events https://www.googleapis.com/auth/calendar.events.readonly https://www.googleapis.com/auth/calendar.readonly https://www.googleapis.com/auth/calendar.settings.readonly profile email`;
    window.location.href = targetUrl;
  };

  return (
    <button
      onClick={handleClick}
      className="items-center bg-slate-900 hover:bg-slate-800 p-2 border-[1.5px] flex gap-2 border-slate-300 rounded-lg text-slate-200 hover:border-white hover:text-white hover:shadow transition duration-150"
    >
      <img
        className="w-5 h-5"
        src="https://www.svgrepo.com/show/475656/google-color.svg"
        loading="lazy"
        alt="google logo"
      />
      <span className="font-bold">Login</span>
    </button>
  );
}

export default GoogleLoginButton;
